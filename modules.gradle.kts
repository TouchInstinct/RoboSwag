val rootDir: String? = if (extra.has("componentsRoot")) {
    extra["componentsRoot"] as String
} else {
    null
}

include(":logging")
include(":utils")
include(":navigation")
include(":storable")
include(":api-logansquare")
include(":lifecycle")
include(":lifecycle-rx")
include(":views")
include(":recyclerview-adapters")
include(":kotlin-extensions")
include(":templates")

project(":utils").projectDir = File (rootDir, "utils")
project(":logging").projectDir = File (rootDir, "logging")
project(":navigation").projectDir = File (rootDir, "navigation")
project(":storable").projectDir = File (rootDir, "storable")
project(":api-logansquare").projectDir = File (rootDir, "api-logansquare")
project(":lifecycle").projectDir = File (rootDir, "lifecycle")
project(":lifecycle-rx").projectDir = File (rootDir, "lifecycle-rx")
project(":views").projectDir = File (rootDir, "views")
project(":recyclerview-adapters").projectDir = File (rootDir, "recyclerview-adapters")
project(":kotlin-extensions").projectDir = File (rootDir, "kotlin-extensions")
project(":templates").projectDir = File (rootDir, "templates")
