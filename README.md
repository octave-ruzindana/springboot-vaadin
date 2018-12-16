# springboot-vaadin
Todo list app with spring boot and vaadin 11

# Evaluation
Great for developers with SWING background.
Great for developers who do not know or do not want to mess with javascript and html (and it's toolings)
Great if you do not want to expose a web service for every single use case. Just plain java services.

Not so good for UI personalisation (standard theme).
Not so good if you need a component or behaviour that is not included in standard library. 

## Productivity
- Great
  - for small to medium projects, very CRUD oriented.
- Not so great
   - for large projects
   
## Maintenability
 - Not so good (at least with java only approach)
   - Depends heavly on developer's own code organisation.
   - No strict guidelines where to put components, views, services,... (as in angular for exemple)
   - Heavy usage of listeners and lamda can make it hard to follow code workflow
## Testability
 -  It's plain java so ... junit, seleniu for UI,...
 
## Security
 - Not tested yet

## Scalability
 - Not tested yet
 
## Internationalisation
 - Easy with spring and properties file

## Learning

 - Great
  - Needs to know java 8 (especially )
  - Official Documentation is quite good 
 - Not so great
  - Not much help outside of official doc (stackoverflow,..), specially for the last version (vaadin 10 and ongoing) 
