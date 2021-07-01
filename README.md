# About
Server provides REST API which combines ERSTE places and weather on selected places. Use path
`/places/weather/coordinates?lat={lat}&lng={lng}&radius={radius}&page={page}&size={size}` to search by coordinates or
`/places/weather/city/{city}?countryCode={countryCode}&page={page}&size={size}` to search by city. Result of both calls
is paginated list of ERSTE places along with current weather on selected place if provided.

Example of response body:

    {
      "type": "BRANCH",
      "state": "OPEN",
      "name": "Blansko",
      "address": "Wanklovo nám. 1436/3",
      "city": "Blansko",
      "country": "CZ",
      "weather": "Clouds",
      "weatherDescription": "overcast clouds",
      "temperature": 291.15
    }

Whole documentation of API is available at `http://localhost:8080/swagger-ui.html` after launch of application.

## Code hierarchy


    ├── controller              # Handles REST API requests
    ├── openapi                 # Structure and configuration for remote API calls
    ├── security                # Configuration of security and api key
    ├── service                 # Components that handles downloading data from remote APIs and provides them to controller
    └── util                    # Tools and utilities


## Parameters
- Add command line argument parameter `erste.apiKey` with your API key to Erste systems

- Add command line argument parameter `openweather.apiKey` with your API key to Open Weather systems
