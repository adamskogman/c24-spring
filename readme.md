## C24 Spring integration ##

This project is an integration library between C24 IO and Spring/Spring Integration. It allows to seamlessly use data model code generated from C24 in Spring application.

### Core support ###

The core support consists of a model abstraction, `HttpMessageConverter` and Spring OXM `(Un)Marshaller` implementations as well as a namespace to configure Spring MVC / OXM infrastructure components:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:c24="http://schema.c24.biz/spring-core">

  <!-- Sets up a C24 model to be used by other components -->
  <c24:model base-package="com.acme.mymodelpackage" />

  <!-- 
       Configures an HttpMessageConverter to support text content 
       (via default text/plain content type) as well as XML content (via
       custom content type).
  -->
  <c24:http-message-converter>
    <c24:format type="TEXT" />
    <c24:format type="XML" content-type="application/vnd.foo+xml" />
  </c24:http-message-converter>

  <!-- Sets up a C24 OXM marshaller -->
  <c24:marshaller />
```

### Spring integration support ###

TBD