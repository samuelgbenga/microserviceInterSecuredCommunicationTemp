

#first step
we created the parent module
using mojo project: pom.xml with is a module for multi-module setup
mvn archetype:generate \                                          
-DarchetypeGroupId=org.codehaus.mojo.archetypes \
-DarchetypeArtifactId=pom-root \
-DarchetypeVersion=RELEASE \
-DgroupId=ng.samuel.mloginregtemp \
-DartifactId=m-login-reg-temp \
-Dversion=0.0.1-SNAPSHOT \
-DinteractiveMode=false

Next we create the module for the other module for the other services.
Discovery-Services you can also call it the registery service.
This is very important for spring cloud distributed system to 
instantiate the inter-communication between each service.

mvn archetype:generate \
-DgroupId=ng.samuel.mloginregtemp \
-DartifactId=discovery-service \
-Dpackage=ng.samuel.mloginregtemp.discoveryservice \
-DinteractiveMode=false

ApiGateway Service is a very important service that streamline the
api access or interaction of each service.
mvn archetype:generate \
-DgroupId=ng.samuel.mloginregtemp \
-DartifactId=api-gateway-service \
-Dpackage=ng.samuel.mloginregtemp.apigatewayservice \
-DinteractiveMode=false


This service should perform the registration and login and generation of jwt of a user.
mvn archetype:generate \
-DgroupId=ng.samuel.mloginregtemp \
-DartifactId=authentication-service \
-Dpackage=ng.samuel.mloginregtemp.authenticationservice \
-DinteractiveMode=false


Mailing Notification Service.
mvn archetype:generate \
-DgroupId=ng.samuel.mloginregtemp \
-DartifactId=mailing-service \
-Dpackage=ng.samuel.mloginregtemp.mailingservice \
-DinteractiveMode=false

Lets update the pom file of the parent pom.xml file 
- connect it to spring as parent
- include important properties java 21 and spring cloud this would be used by the child.
- Include the spring cloud such a way that it would be used by the children. By including it in the dependency management tag

## Let's update the pom file of the Discovery service.
- include the following depencies - for actuator, eureka-server, spring boot starter.

## also include the following in the pom file of the api-gateway 
- this are the dependency - for spring cloud gateway, eureka-client, spring boot starter test.

## also in the authentication service you should include the follwing initial dependency?
- jpa, h2 db (we will be using this), eureka-client, spring boot starter test

## also in the mailing service you should include the follwing initial dependency?
-  eureka-client, spring boot starter test it doesnt need any jpa we will include other necessary dependcies later

Next if you notice in each module there is no resouce folder. so we are going to include that
and setup the application properties for the Discovery service and the Api-gateway would be application.yml 
first before we set up the application properties for the other two services.

Steps to manualy create the resource directory.
right click on the "main" package 
select new -> select directory.
by default there should be an already existing "resource" option to select.
select the "resource" option.
Click enter
Do that for all the services.

## Initial Application properties Settings 
- If you done with that. lets setup the application.properties of the discovery serivice.
- Authentication service
- Mailing Service
- Lastly we are going to set up the application.yml of the Gateway service.

## Next you can refactor the default "App" name of the entry point class to fit the service.
eg. MailingApp.java, AuthenticationApp.java, DiscoveryApp.java, MailingApp.java
also include the @SpringBootApplication and also the and also include @EnableEurekaServer on the Discovery-service
and @EnableEurekaClient on the other services.
public static void main(String[] args) {
SpringApplication.run(name-of-entry-point-class.class, args);
}
to each entry point on the classes


## let us set up the authentication service
i will skip the coding process for this but i will show where significant steps or inclusing is needed.

## i will not be going into detail coding on how to setup mail service or authentiction service.
## what i will be showing are the essential codes the needs modifying which is not much though. especially in the
## security and how your dto should look like. 
- for the security we will be generating the jwt on the authentication service normal traditional security settings
- can get this done.
- While for the mailing service we will make few adjustment since it would be generating a jwt but rather validating it
- and extracting claims and also would not need an explicit userDetailsService map to any user since there is no entity.
- the userdetails info would be extracted from the jwt token. and send to the security context.
- While for the dto. the standard mailindto we will be using. should be reflecting for both service authentication service and 
- mailing service which a record that contains to, subject, fullName, and event.
- Here are simple template to get you started on seting up a mail-service and a the authentication service respectively.
- i have added the link to the actual complete code below so you can compare and contrast and understand the changes made.
- not withstanding i will present the changes which are essential for this setup. which i already explain though.
- 




