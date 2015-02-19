# SendJMSWorkItem

This is very a simple implementation of a WorkItemHandler used to send a JMS message to a activemq queue. Release 6.0.3

#Data input parameters:

    messageIn: String contains the body message
    resumeTask: String can be two values:
        "resume": if there are problems with Camel route and throws a Camel Exception, the process instance will be created anyway or the process will resume executing.
        "whatever": if there is any problem, the process throws an exception and the process instance end or stops here. This is the default behaviour of BPM.
    incidenceSignal: String contains the signal of the incidence subprocess will launch. It has sense when you want move the process instance to a safe task when resume parameter equals to "resume" and a Camel Exception happens.

#Data output parameter:

    JMSStatus: String.

#In versión BPMS 6.0.3 you need copy these libraries into /WEB-INF/lib directory

spring-jms-3.1.0.RELEASE.jar

spring-web-3.1.0.RELEASE.jar

spring-core-3.1.0.RELEASE.jar

spring-context-3.1.0.RELEASE.jar

spring-tx-3.1.0.RELEASE.jar

spring-expression-3.1.0.RELEASE.jar

spring-beans-3.1.0.RELEASE.jar

spring-aop-3.1.0.RELEASE.jar

spring-asm-3.1.0.RELEASE.jar

commons-net-1.4.1.jar

spring-core-3.2.11.RELEASE.jar

camel-core-2.14.0.jar

spring-jdbc-3.2.11.RELEASE.jar

spring-beans-3.2.11.RELEASE.jar

spring-tx-3.2.11.RELEASE.jar

camel-sql-2.14.0.jar
