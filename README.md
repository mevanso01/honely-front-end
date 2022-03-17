# honely-front-end

## Overview
The honely project is designed to serve the front end of honely.com
as well as providing some backend services and proxying the data api.

### Project structure
The project consists of 3 subprojects that are designed to be deployed
as microservices in docker containers.

The microservices should be run behind a proxy or proxying webserver.
The standard options are haproxy, or nginx, or possibly apache.  A sample
haproxy config file is checked into docs.   A docker-compose example is
also checked in for staging the site. In production, each container would
be deployed as a system service, or in a kubernetes environment.

Gradle is used as a build tool.

Additionally, the Facebook and Google logins need to be set up.  See the
section "Auth" below.

## Subprojects

### web-app

The web-app runs a simple webserver that serves the static front end.
The front end is a Vue.js project.

To build the web-app service:
```
./gradlew :web-app:shadowJar
```
This will build a jar file in web-app/build/lib/web-app-fat.jar

There is also a jib configuration that can be used to deploy to a registry server
for pulling into a docker deployment.

### public-api

This is the backend code.

Initialize Permissions and Roles:

Apply the sql file 'init-perms-and-roles.sql' to the honely database

pem keys need to be generated
```
> cd public-api/pem_keys
> ./generate-keys-for-jwt.sh
```

To run the back end locally:
```
> ./gradlew :public-api:run
```
To build the public-api service:
```
> ./gradlew :public-api:shadowJar
```
This will build a jar file in public-api/build/lib/public-api-fat.jar

There is also a jib configuration that can be used to deploy to a registry server
for pulling into a docker deployment.


### blog-service

This is a service for uploading and serving blog pages.

To run the back end locally:
```
> ./gradlew :blog-service:run
```
To build the blog-service app:
```
> ./gradlew :blog-service:shadowJar
```
This will build a jar file in blog-service/build/lib/blog-service-fat.jar

There is also a jib configuration that can be used to deploy to a registry server
for pulling into a docker deployment.

Blog posts are uploaded as Markdown files.  Examples are
in docs/insights_posts.

To upload blog posts:

```
curl -F upload=@"filename.md" https://honely.com/api/v1/post
```



## Auth

### Facebook

Got to https://developers.facebook.com/

Click "My Apps" in the upper right
Click "Create App"
Select "Consumer" from the list of options

Give the app a name, and a contact email
Click Ok

Once the app is created you'll be on the dashboard for that app
There should be a grid of 6 squares in the middle.
The upper right one should save "Facebook Login" - click "Setup" on that

Click "Web"

Enter the site url and click save

Ignore the rest, on the left click Settings -> Basic

In the form, enter the app domain, the privacy policy url (e.g. https://honely.metarhythm.com/privacy), and a data deletion url , then save changes

Copy the app id and app secret from the top of this form

These get set to env variables FB_CLIENT_ID and FB_CLIENT_SECRET respectively for the auth-api

### Google

Go to the google developers console: https://console.developers.google.com

On the left side, click "Credentials"

Click "Create credentials" at the top and select "Oauth client id"

Application type is "Web application"

Under the Authorized redirect uris enter the domain + '/auth/api/v1/google-callback'   e.g.
https://honely.metarhythm.com/auth/api/v1/google-callback

Copy the app id and secret

These are set as the GOOGLE_CLIENT_ID and GOOGLE_CLIENT_SECRET env variables for the auth-api


## Development

#### To start external app dependencies
```
> docker-compose up
```

credentials are user: meta pass: rh1thm

#### Running the front end for development
```
> cd web-app
> yarn
> yarn serve
```

To use local variables before running the web app:
```
> cd web-app
> cp env.development.local .env.development.local
```



