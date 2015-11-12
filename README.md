### How to include it in your project
If you are using Maven as your dependency manager you can include the JAR dependency like this (is not in maven central repository, you need to download it and compile it):

      <dependency>
         <groupId>org.rage</groupId>
         <artifactId>syring</artifactId>
         <version>1.1.0-SNAPSHOT</version>
      </dependency>
      
Also, we need to create a file `prop-config.properties` in case you will use FILE/JNDI options and put it inside the resources folder in order to be available to the component class loader resource locator. In this file we need to define these 3 properties:

    properties.variables=
    jndi.variable=
    jndi.client.context.factory.variable=
    
- `properties.variables` may have several system variables separated by `,` where you may have different properties files.
- `jndi.variable` is currently not being used, but in a future release it will hold the system variable to obtain the JNDI Provider URI.
- `jndi.client.context.factory.variable` is the FQN of the class used to connect to the JNDI Server Directory. By default the frameworks provides a constant using JBoss JNP Client (`org.jnp.interfaces.NamingContextFactory`). Please note that this framework does not bundle any JNDI clients, thus you must add dependencies to any classes needed for this purpose. For example for JBoss JNPO client:

         <dependency>
            <groupId>jboss</groupId>
            <artifactId>jnp-client</artifactId>
            <version>4.0.2</version>
         </dependency>

For a reference implementation you may check [this git project:](https://github.com/roar109/syring-example) 


### System properties
    @Inject @ApplicationProperty(name="service.endpoint.jndi.location", type=ApplicationProperty.Types.SYSTEM)
    private String jndiUrl;

### File properties

    @Inject @ApplicationProperty(name="sample.prop.value", type=ApplicationProperty.Types.FILE)
    private String allowEventRegistration;


### JNDI values

    @Inject @ApplicationProperty(name="project/sample/jndi", type=ApplicationProperty.Types.JNDI)
    private String jndiUrl;

### File + JNDI properties

    @Inject @ApplicationProperty(name="data.jndi.url", type=ApplicationProperty.Types.FILE_JNDI)
    private String allowEventRegistration;

When you have something like this in your project properties file `data.jndi.url=server/sample/url` this will get the value from your properties file and then look for the jndi definition of it. Follow the same rules as the File and JNDI options.