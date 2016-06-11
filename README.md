# Linear Layout Manager

## DEPRECATED
RecyclerView supports WRAP_CONTENT starting from Android Support Library 23.2. More details here: http://android-developers.blogspot.se/2016/02/android-support-library-232.html

## Description
Implementation of [LinearLayoutManager](https://developer.android.com/reference/android/support/v7/widget/LinearLayoutManager.html) which wraps its contents.

Usage example:
<pre><code>final LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
recyclerView.setLayoutManager(layoutManager);
recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
recyclerView.setAdapter(adapter);</code></pre>

Note that if the child views in your RecyclerView have the fixed size LinearLayoutManager#setChildSize should be used
to avoid unnecessary measuring.

## Installation
Gradle dependency:
<pre><code>compile 'org.solovyev.android.views:linear-layout-manager:0.5@aar'</code></pre>
Maven dependency:
```xml
<dependency>
    <groupId>org.solovyev.android.views</groupId>
    <artifactId>linear-layout-manager</artifactId>
    <version>0.5</version>
    <type>apklib</type>
</dependency>
```

## License
Library is distributed under Apache 2.0 license, see LICENSE.txt

## Applications

The following applications use this library:
* [Sample app](https://oss.sonatype.org/content/repositories/releases/org/solovyev/android/views/linear-layout-manager-app/)
* [Say it right!](https://play.google.com/store/apps/details?id=org.solovyev.android.dictionary.forvo)
