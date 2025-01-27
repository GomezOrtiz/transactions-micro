# Transactions Microservice

To run the app, clone the repo and then run in terminal from project root:

```
docker-compose up
```

Then, access this url from your browser:

> http://localhost:8080/api/docs

Then you can make requests to any endpoint as you wish, but be aware that valid accounts for transactions are limited. For testing purposes, you can check which accounts are valid to create a new transaction by making a GET request to /api/account/all endpoint.

Also note that reference to create transaction, if present, must be a valid UUID in String format.

So easy! :+1:

What if it didn't work? :scream: :scream: Don't panic. Try the following:

- Run the command with sudo.
- Check that port 8080 is free in your machine. If not, kill that port process or change docker-compose.yml port mapping.
- Check you have Docker and Docker Compose properly installed.

