# About
Server provides REST API which combines ERSTE places and weather on selected places. Use path
`/places/weather/coordinates?lat={lat}&lng={lng}&radius={radius}&page={page}&size={size}` to search by coordinates or
`/places/weather/city/{city}?countryCode={countryCode}&page={page}&size={size}` to search by city. Result of both calls
is paginated list of ERSTE places along with current weather on selected place if provided.

# Parameters
- Add command line argument parameter `erste.apiKey` with your API key to Erste systems

- Add command line argument parameter `openweather.apiKey` with your API key to Open Weather systems
