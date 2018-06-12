# Mobimeo Backend Engineering Challenge

## Problem

In the fictional city of Verspaetung, public transport is notoriously unreliable. To tackle the problem,
the city council has decided to make the public transport timetable and delay information public,
opening up opportunities for innovative use cases.

You are given the task of writing a web API to expose the Verspaetung public transport information.

As a side note, the city of Verspaetung has been built on a strict grid - all location information can be
assumed to be from a cartesian coordinate system.

## Data

The Verspaetung public transport information is comprised of 4 CSV files:

- `lines.csv` - the public transport lines.
- `stops.csv` - the stops along each line.
- `times.csv` - the time vehicles arrive & depart at each stop. The timetimestamp is in the format of `HH:MM:SS`.
- `delays.csv` - the delays for each line. This data is static and assumed to be valid for any time of day.

## Challenge

A simple Swagger file containing two endpoints and the expected behaviour of each is included.

Build a web API which implements this specification.

Endpoints should be available via port `8081`

## Implementation

You can choose any programming language you are familiar with.

Make sure to follow general software development best practices and best practices of the respective language.

## Delivery

Create a git repository and share the code with us through GitHub or zip file.
