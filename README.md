# Note to the reviewers

Thanks for giving this challenge, it has been a real joy to develop a near-real-life example application.
The project passes al tests and code quality checks (scalastyle, scapegoat). The implementaion
is not always perfect for a more generic use cases than specified in the example. Simplifications that were made
here but might need improvement later:
 - adding delay to the specified time is made only for minutes, considering that for all timestamps
   adding the respective delay would not flip the hour
 
The code contains some comments to explain some trade offs and possible improvements.

## Instructions
### Execute tests and code quality

```
sbt clean coverage test coverageReport scalastyle scapegoat
```

### Run locally (test mode)

```
sbt run 8081
```
### Check endpoints
```
curl -v localhost:8081/lines/S75
```

## Comments on the API

1. I would wrap the return type of the `/lines/{name}` endpoint in an object for extensibility. This way, instead
of returning

`"true"`

we could return

`{"line": "M4", "delayed": "true", "reason": "Baustelle"}`

2. The endpoint `/lines/{name}` does not return a resource. Instead, something like `/line-delays/{name}`
could describe the delay resource and give more valuable information than just a boolean. 
