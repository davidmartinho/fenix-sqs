package eu.ist.fenixcloud.sqs;

import eu.ist.fenixcloud.sqs.domain.SimpleQueueService;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;

public class Bootstrap {

  public static void init() {
    try {
      FenixFramework.initialize(new Config() {{
        domainModelPath = PropertiesManager.getProperty("dml.filename");
        dbAlias = PropertiesManager.getProperty("sql.alias");
        dbUsername = PropertiesManager.getProperty("sql.username");
        dbPassword = PropertiesManager.getProperty("sql.password");
        rootClass = SimpleQueueService.class;
      }});
    } catch(Error e) {
      
    }
  }
}
