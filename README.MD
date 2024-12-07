# WeazleTimeTracking Applincation

Time tracking for the rest of us.

## Local Development with podman

```bash
$ mkdir -p .docker_volumes/postgres #Ensure local directories for development exits
$ podman run --name postgres -e POSTGRES_USER=username -e POSTGRES_PASSWORD=password -p 5432:5432 -v ./.docker_volumes/postgres -d postgres
```