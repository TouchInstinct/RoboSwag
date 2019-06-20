# RoboSwag

Roboswag - это набор модулей, расширяющих возможности работы с `Android SDK` и `ReactiveX`.


### Требования

* Andoroid Api: 19
* Kotlin: 1.3.11
* Gradle: 3.2.1
* Gradle CPD Plugin: 1.1
* Detect Plugin: 1.0.0-RC12

### Подключение

.gitmodules

```
[submodule "RoboSwag"]
	path = RoboSwag
	url = git@github.com:TouchInstinct/RoboSwag.git
```

build.gradle

```gradle
dependencies {
    implementation project(':utils')
    implementation project(':views')
    implementation project(':storable')
    implementation project(':logging')
    implementation project(':api-logansquare')
    implementation project(':lifecycle')
    implementation project(':lifecycle-rx')
    implementation project(':navigation')
    implementation project(':templates')
    implementation project(':recyclerview-adapters')
    implementation project(':recyclerview-calendar')
    implementation project(':kotlin-extensions')
    implementation project(':livedata-location')
}
```
Можно подключать только те модули, которые вам необходимы.

### R8/Proguard

```
-keep class ** extends ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController { *; }
```

### Лицензия

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
