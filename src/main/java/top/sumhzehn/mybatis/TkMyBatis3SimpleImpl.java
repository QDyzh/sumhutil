package top.sumhzehn.mybatis;

import java.text.MessageFormat;

import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3SimpleImpl;
import org.mybatis.generator.internal.util.StringUtility;

public class TkMyBatis3SimpleImpl
  extends IntrospectedTableMyBatis3SimpleImpl
{
  protected String calculateMyBatis3XmlMapperFileName()
  {
    StringBuilder sb = new StringBuilder();
    if (StringUtility.stringHasValue(this.tableConfiguration.getMapperName()))
    {
      String mapperName = this.tableConfiguration.getMapperName();
      int ind = mapperName.lastIndexOf('.');
      if (ind != -1) {
        mapperName = mapperName.substring(ind + 1);
      }
      sb.append(MessageFormat.format(mapperName, new Object[] { this.fullyQualifiedTable.getDomainObjectName() }));
      sb.append(".xml");
    }
    else
    {
      sb.append(this.fullyQualifiedTable.getDomainObjectName());
      sb.append("Mapper.xml");
    }
    return sb.toString();
  }
  
  protected void calculateJavaClientAttributes()
  {
    if (this.context.getJavaClientGeneratorConfiguration() == null) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(calculateJavaClientImplementationPackage());
    sb.append('.');
    sb.append(this.fullyQualifiedTable.getDomainObjectName());
    sb.append("DAOImpl");
    setDAOImplementationType(sb.toString());
    
    sb.setLength(0);
    sb.append(calculateJavaClientInterfacePackage());
    sb.append('.');
    sb.append(this.fullyQualifiedTable.getDomainObjectName());
    sb.append("DAO");
    setDAOInterfaceType(sb.toString());
    
    sb.setLength(0);
    sb.append(calculateJavaClientInterfacePackage());
    sb.append('.');
    if (StringUtility.stringHasValue(this.tableConfiguration.getMapperName()))
    {
      sb.append(MessageFormat.format(this.tableConfiguration.getMapperName(), new Object[] { this.fullyQualifiedTable.getDomainObjectName() }));
    }
    else
    {
      sb.append(this.fullyQualifiedTable.getDomainObjectName());
      sb.append("Mapper");
    }
    setMyBatis3JavaMapperType(sb.toString());
    
    sb.setLength(0);
    sb.append(calculateJavaClientInterfacePackage());
    sb.append('.');
    if (StringUtility.stringHasValue(this.tableConfiguration.getSqlProviderName()))
    {
      sb.append(MessageFormat.format(this.tableConfiguration.getSqlProviderName(), new Object[] { this.fullyQualifiedTable.getDomainObjectName() }));
    }
    else
    {
      sb.append(this.fullyQualifiedTable.getDomainObjectName());
      sb.append("SqlProvider");
    }
    setMyBatis3SqlProviderType(sb.toString());
  }
}
