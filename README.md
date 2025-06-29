# GitHub Repository Ranking

GitHub Repository Ranking is a Java backend application that allows to search for repositories on GitHub and scoring them by popularity.

## Installation

### Required dependencies:

- Docker
- Java 21
- Maven

### Environment properties:

The following environment properties must be set in order to run this service:
```text
GITHUB_SEARCH_API_BASE_URL=https://api.github.com
GITHUB_SEARCH_API_ACCESS_TOKEN=<YOUR_PERSONAL_GITHUB_TOKEN>
```
For this coding challenge I've used a GitHub personal access token (PAT) as a matter of convenience and speed.\
To get a GitHub Token, take a look [here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens).

### Running the service:

- Clone this repository and go to the projects root folder.
- Remember to set up your `GITHUB_SEARCH_API_ACCESS_TOKEN` variable:
    ```bash
     export GITHUB_SEARCH_API_ACCESS_TOKEN="<YOUR_PERSONAL_GITHUB_TOKEN>"
    ```
- Run `docker-compose`:
    ```bash
    docker-compose up
    ```
- Or, if you want to run it in the background, detached:
    ```bash
    docker-compose up -d
    ```

## Usage

- The Open API specs are provided at `docs/api-specs/repository-ranking-api-v1.json`.
- An [Insomnia](https://insomnia.rest/) collection is provided at `docs/insomnia-collection/insomnia_repository_ranking.yaml`
- When the app is running, you can access the Api documentation at: http://localhost:8080/swagger-ui/index.html

## Roadmap

Here list the improvements I would do:

- the handling of the pagination, I would improve it to use the `links` header as described [here](https://docs.github.com/en/rest/using-the-rest-api/using-pagination-in-the-rest-api?apiVersion=2022-11-28)
- rate limiter, circuit-breaker weren't added to this implementation
- the cache is in memory (Caffeine), I would use a better provider, i.e. Redis, Memcached, Hazelcast, etc
- a [GitHub Actions](https://github.com/features/actions) pipeline could be configured for CI/CD

## Notes

- the processing of the repository items and the popularity calculation is synchronous, with bigger data volumes or complex calculations,\
  I could rather use a scheduled approach
- no storage solution was implemented, as the popularity is calculated 'on the fly'

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Author

[Eduardo Ducer](mailto:educer@gmail.com)

## License

[MIT](https://choosealicense.com/licenses/mit/)
