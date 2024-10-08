# spring-testcontainers-registry

Demo of an error that occurs when starting a docker registry in testcontainers and try to push to this registry. 

# registry as plain docker container

Push to the registry started as a plain docker container work fine:

```shell
% docker run -d --rm -p 5000:5000 --name registry registry:2
443f9ea881257529793b059442a24be96e992123a484b756b8d1ae41eec4a332

% docker pull hello-world
Using default tag: latest
latest: Pulling from library/hello-world
Digest: sha256:91fb4b041da273d5a3273b6d587d62d518300a6ad268b28628f74997b93171b2
Status: Image is up to date for hello-world:latest
docker.io/library/hello-world:latest
What's Next?
  1. Sign in to your Docker account → docker login
  2. View a summary of image vulnerabilities and recommendations → docker scout quickview hello-world

% docker tag hello-world localhost:5000/hello-world

% docker push localhost:5000/hello-world
Using default tag: latest
The push refers to repository [localhost:5000/hello-world]
12660636fe55: Pushed 
latest: digest: sha256:a8ea96bb64d60208d6a56712042d1cf58aa4a7d3751b897b9320b0813c81cbb4 size: 524
```

# registry as testcontainers container

Push to the registry started in testcontainers does not work.

How to reproduce:

- Set breakpoint in `TestcontainersTest` class, on `"set breakpoint here"` line.
- Debug `TestcontainersTest.test()` in IDEA
- When paused, notice the mapped port for the registry container, e.g. `57025`:
  ```shell
  docker ps | grep registry
  ```
- Pull and push image to registry - errors:
```
% docker pull hello-world
Using default tag: latest
latest: Pulling from library/hello-world
478afc919002: Pull complete 
Digest: sha256:91fb4b041da273d5a3273b6d587d62d518300a6ad268b28628f74997b93171b2
Status: Downloaded newer image for hello-world:latest
docker.io/library/hello-world:latest
What's Next?
  1. Sign in to your Docker account → docker login
  2. View a summary of image vulnerabilities and recommendations → docker scout quickview hello-world

% docker tag hello-world localhost:57025/hello-world

% docker push localhost:57025/hello-world
Using default tag: latest
The push refers to repository [localhost:57025/hello-world]
Get "http://localhost:57025/v2/": dial tcp [::1]:57025: connect: connection refused

% docker tag hello-world 127.0.0.1:57025/hello-world
% docker push 127.0.0.1:57025/hello-world
Using default tag: latest
The push refers to repository [127.0.0.1:57025/hello-world]
Get "http://127.0.0.1:57025/v2/": dial tcp 127.0.0.1:57025: connect: connection refused

% docker tag hello-world 0.0.0.0:57025/hello-world
% docker push 0.0.0.0:57025/hello-world
Using default tag: latest
The push refers to repository [0.0.0.0:57025/hello-world]
Get "https://0.0.0.0:57025/v2/": http: server gave HTTP response to HTTPS client
```