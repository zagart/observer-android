<h1>Daily progress journal</h1>
<ul>
	<li>
		<ul>
			<h4>29.09.2016</h4>
			<li>
				AVD configured and first app run executed. 
				Read information about Android-developer workflow basics.
			</li>
			<li>
				Read information about mipmap - it is laucher icon, which is have 
				version for all density types of screens. Before compiling .apk file 
				icons with unnessasary density just cuted out from target build.
			</li>
			<li>
				Android 4.4 (API level 20) and lower doesn't support vector drawables.
				For backward compatibilty Vector Asset Studio generate raster images. 
				To use this features requied Java abstact class Drawable or @drawable in
				XML-code. For using only vector drawables required Android Support Library 23.2,
				which is may be included by adding next code to build.gradle:<br/>
				<pre><code>
				android {
				  defaultConfig {
				    vectorDrawables.useSupportLibrary = true
				  }
				}
				</code></pre>
				In newest versions of Android SDK this code generates automatically.
			</li>
		</ul>
	</li>
	<li>
		<ul>
			<h4>30.09.2016</h4>
			<li>
				Assets - it is arbitrary files which includes into project. Main difference from
				the resource files is that Android do not include this files into resource system.
				That allows to get raw data.
			</li>
			<li>
				PermissionRequest sample was loaded and runned.
			</li>
			<li>
				AVD already has pre-installed Api-Demos application. I've found reference to
				google sample but seems it something different from what i need 
				(<a href="https://github.com/googlemaps/android-samples/tree/master/ApiDemos">link</a> 
				    - that Maps API, don't think that is what i actually need. I keep on search).			
				Now I'm going to find out it. Also Android Studio has similar sample but reference to
				repository is dead.
				
			</li>
		</ul>
	</li>
</ul>