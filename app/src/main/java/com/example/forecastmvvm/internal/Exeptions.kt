package com.example.forecastmvvm.internal

import java.io.IOException

class NoConnectivityException(message: String) : IOException(message)
class LocationPermissionNotGrantedException: Exception()
class DateNotFoundException: Exception()
class ApiException(message: String) : IOException(message)
