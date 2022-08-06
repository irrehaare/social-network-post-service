# Social Network Posts Microservice
## Description
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

## Design decisions
### Health endpoints
By the assumption that microservices function in the environment consisting of many of them, for the purpose of 
monitoring this microservice it includes a (mock) of health endpoints:
- /health with full health status data and description,
- /is-healthy with boolean reporting if the service is healthy or not.

### Programming paradigms
As I've recently attended a conference focused on functional programming I tried to apply some ideas from it in 
this project.
1. Make illegal states unrepresentable.
2. No side effects.

## Personal comments
There is a number of practices where the best approach is heavily disputed in the programming industry. Let me explain 
my choices for this microservice
### REST API path - plural vs singular
I've decided to name the api as /api/v1/social-network-posts (plural) as I agree that it's supposed to contain a 
number of posts and GET method without specific id will provide all posts (also plural).
### Spring boot framework
In my current position I only have contact with Spring when working on legacy code, as we create new microservices 
without such heavy dependencies. The logic behind it sums up to saying "If it benefits from using Spring, it's not 
micro anymore". Because of that I haven't always been able to fully utilize Spring functionalities.