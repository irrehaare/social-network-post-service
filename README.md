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
2. No side effects (when possible within my skills).

### Deleting objects
I have implemented a possibility to permanently delete data from the DB, however in a default behaviour (without 
query parameter permanent=true) it only marks IS_DELETED value to true, which causes the object to act within the 
API as if it is deleted, but the data is kept in DB in case it's required later (e.g. because of some legal action).  

### Updating view counts
1. I've implemented a separate endpoint with the assumption, that there will be clients (web or phone app) that 
detect post being scrolled or opened into user's view and only then send request to increment view count
2. I'm aware that putting action (increment) into path is not fully RESTful solution, but after quick research
I've decided that's the least evil option of all. In professional environment I'd check company guidelines 
and ask colleagues if necessary.
3. I've added a possibility to increment views when getting the posts just in case.
    
## Personal comments
There is a number of practices where the best approach is heavily disputed in the programming industry. When working 
with a team I always follow agreed guidelines with no regard for my personal preferences. Let me explain my choices 
for this microservice. 

### REST API path - plural vs singular
I've decided to name the api as /api/v1/social-network-posts (plural) as I agree that it's supposed to contain a 
number of posts and GET method without specific id will provide all posts (also plural).

### Spring boot framework
In my current position I only have contact with Spring when working on legacy code, as we create new microservices 
without such heavy dependencies. The logic behind it sums up to saying "If it benefits from using Spring, it's not 
micro anymore". Because of that I haven't always been able to fully utilize Spring functionalities.