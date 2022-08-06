#Social Network Posts Microservice
##Description
This is a springboot microservice providing a REST CRUD API for the social network posts. The object is defined as follows 
(in pseudo-code):
```
SocialNetworkPost {
    Date postDate
    String author
    String content
    Number viewCount
}
```

##Design decisions
###Health endpoints
By the assumption that microservices function in the environment consisting of many of them, for the purpose of 
monitoring this microservice it includes a (mock) of health endpoints:
- /health with full health status data and description,
- /is-healthy with boolean reporting if the service is healthy or not.

###Programming paradigms
As I've recently attended a conference focused on functional programming I tried to apply some ideas from it in this project.
1. Make illegal states unrepresentable.
2. No side effects.