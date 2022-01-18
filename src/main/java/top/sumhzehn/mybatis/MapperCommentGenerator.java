package top.sumhzehn.mybatis;

import java.text.MessageFormat;
import java.util.Properties;
import java.util.Set;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

public class MapperCommentGenerator implements CommentGenerator {
	private String beginningDelimiter = "";
	private String endingDelimiter = "";

	public void addJavaFileComment(CompilationUnit compilationUnit) {
	}

	public void addComment(XmlElement xmlElement) {
		xmlElement.addElement(new TextElement("<!--"));
		StringBuilder sb = new StringBuilder();
		sb.append("  WARNING - ");
		sb.append("@mbg.generated");
		xmlElement.addElement(new TextElement(sb.toString()));
		xmlElement.addElement(new TextElement("-->"));
	}

	public void addRootComment(XmlElement rootElement) {
	}

	public void addConfigurationProperties(Properties properties) {
		String beginningDelimiter = properties.getProperty("beginningDelimiter");
		if (StringUtility.stringHasValue(beginningDelimiter)) {
			this.beginningDelimiter = beginningDelimiter;
		}
		String endingDelimiter = properties.getProperty("endingDelimiter");
		if (StringUtility.stringHasValue(endingDelimiter)) {
			this.endingDelimiter = endingDelimiter;
		}
	}

	public String getDelimiterName(String name) {
		StringBuilder nameBuilder = new StringBuilder();
		nameBuilder.append(this.beginningDelimiter);
		nameBuilder.append(name);
		nameBuilder.append(this.endingDelimiter);
		return nameBuilder.toString();
	}

	protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
		StringBuilder sb = new StringBuilder();
		sb.append(" * ");
		sb.append("@mbg.generated");
		if (markAsDoNotDelete) {
			sb.append(" do_not_delete_during_merge");
		}
		javaElement.addJavaDocLine(sb.toString());
	}

	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
	}

	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		StringBuilder sb;
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			field.addJavaDocLine("/**");
			sb = new StringBuilder();
			sb.append(" * ");
			sb.append(introspectedColumn.getRemarks());
			field.addJavaDocLine(sb.toString());
			field.addJavaDocLine(" */");
		}
		if (field.isTransient()) {
			field.addAnnotation("@Transient");
		}
		for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
			if (introspectedColumn == column) {
				field.addAnnotation("@Id");
				break;
			}
		}
		String column = introspectedColumn.getActualColumnName();
		if ((StringUtility.stringContainsSpace(column))
				|| (introspectedTable.getTableConfiguration().isAllColumnDelimitingEnabled())) {
			column = introspectedColumn.getContext().getBeginningDelimiter() + column
					+ introspectedColumn.getContext().getEndingDelimiter();
		}
		if (!column.equals(introspectedColumn.getJavaProperty())) {
			field.addAnnotation("@Column(name = \"" + getDelimiterName(column) + "\")");
		} else if ((StringUtility.stringHasValue(this.beginningDelimiter))
				|| (StringUtility.stringHasValue(this.endingDelimiter))) {
			field.addAnnotation("@Column(name = \"" + getDelimiterName(column) + "\")");
		}
		if (introspectedColumn.isIdentity()) {
			if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement().equals("JDBC")) {
				field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
			} else {
				//field.addAnnotation("@Id");
				field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
			}
		} else if (introspectedColumn.isSequenceColumn()) {
			String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
			String sql = MessageFormat.format(
					introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement(),
					new Object[] { tableName, tableName.toUpperCase() });
			field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY, generator = \"" + sql + "\")");
		}
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
	}

	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
	}

	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
	}

	public void addGetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		StringBuilder sb = new StringBuilder();
		method.addJavaDocLine("/**");
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			sb.append(" * 获取");
			sb.append(introspectedColumn.getRemarks());
			method.addJavaDocLine(sb.toString());
			method.addJavaDocLine(" *");
		}
		sb.setLength(0);
		sb.append(" * @return ");
		sb.append(introspectedColumn.getActualColumnName());
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			sb.append(" - ");
			sb.append(introspectedColumn.getRemarks());
		}
		method.addJavaDocLine(sb.toString());
		method.addJavaDocLine(" */");
	}

	public void addSetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		StringBuilder sb = new StringBuilder();
		method.addJavaDocLine("/**");
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			sb.append(" * 设置");
			sb.append(introspectedColumn.getRemarks());
			method.addJavaDocLine(sb.toString());
			method.addJavaDocLine(" *");
		}
		Parameter parm = (Parameter) method.getParameters().get(0);
		sb.setLength(0);
		sb.append(" * @param ");
		sb.append(parm.getName());
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			sb.append(" ");
			sb.append(introspectedColumn.getRemarks());
		}
		method.addJavaDocLine(sb.toString());
		method.addJavaDocLine(" */");
	}

	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
	}

	@Override
	public void addClassAnnotation(InnerClass arg0, IntrospectedTable arg1, Set<FullyQualifiedJavaType> arg2) {

	}

	@Override
	public void addFieldAnnotation(Field arg0, IntrospectedTable arg1, Set<FullyQualifiedJavaType> arg2) {

	}

	@Override
	public void addFieldAnnotation(Field arg0, IntrospectedTable arg1, IntrospectedColumn arg2,
			Set<FullyQualifiedJavaType> arg3) {

	}

	@Override
	public void addGeneralMethodAnnotation(Method arg0, IntrospectedTable arg1, Set<FullyQualifiedJavaType> arg2) {

	}

	@Override
	public void addGeneralMethodAnnotation(Method arg0, IntrospectedTable arg1, IntrospectedColumn arg2,
			Set<FullyQualifiedJavaType> arg3) {

	}
}
