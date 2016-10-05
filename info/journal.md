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
				That's it - <a href="https://github.com/android/platform_development/tree/master/samples/ApiDemos">ApiDemos</a>.				
			</li>
			<li>
				Taked a look at Android Monitor. I found out how to find nessesary process and analyse it.
			</li>
		</ul>
	</li>
	<li>
		<ul>
			<h4>01.10.2016</h4>
			<li>
				Read information at <a href="developer.android.com">developer.android.com<a/> about Views and
				Layouts. Now i am training at coding xml-layouts and i am going to build simple UI-application
				just to take view about Android makeup. 
			</li>
		</ul>
	</li>
	<li>
		<ul>
			<h4>02.10.2016</h4>
			<li>
				Learned and documented at paper theory about Views, Activities, Intents and life cycle of Activity.
				Also made simple application which demonstrates work of methods that correspond to the same items 
				of Activity life cycle.
			</li>
		</ul>
	</li>
	<li>
		<ul>
			<h4>03.10.2016</h4>
			<li>
				Learned and documented at paper theory about Fragments, their life cycle, Services, their types and
				ways to implement.
			</li>
			<li>
				Saving and restoring instance of activity happens at methods onSaveInstanceState and onRestoreInstanceState
				accordingly.
			</li>
			<li>
				Flags which can be added to Intents describe content of concrete Intent object and behavior
				this object may invoke. There are two main types of attributes (action, data) and four secondary
				types (category, type, component, extras). Each type has his own flags.
			</li>
			<li>
				Services can be of two types: started and bound. Started services run without limitation in time
				and bound services exist while exists dependent object (client in term of bound services).
				Bound services may be implemented in three ways: threw IBinder, Messenger or AIDL. IBinder use
				when service and it's invoker exist at same process. Messenger use in IPC (interprocess connection).
				Messenger is implementation of AIDL so in most cases better to use Messenger.
			</li>
			<li>
				By default Service implementations lie at thread where they have been called. Using services in that way
				may make negative influence on main operations of applications. But Service has child that
				runs at new separate thread - IntentService. Foreground service it is service which is
				required to user and is not candidate to kill when low on memory. Foreground  service must provide
				notification in status bar while he is active.
			</li>
		</ul>
	</li>
	<li>
		<ul>
			<h4>04.10.2016</h4>
			<li>
				Work was finished  under task with saving activities state. There were the little problem and the big 
				problem. Little problem is that Android destroys activity when user press button Back. And method
				onSaveInstanceState() do not work in that case so i solved this problem  with overriding behavior of 
				back button. And the big problem - i almost haven't good skills in makeup. I work on it now.
			</li>
			<li>
				Task is a group of activities that have same application area. Back stack (stack) is just a way
				how activities lie in task.
			</li>
			<li>
				So I found that launch mode of activity is a way activity start or, if to be concrete, way of starting 
				activity when it's position in back stack defined by four modes. Each of modes can be called by adding 
				flag to intent.
			</li>
		</ul>
	</li>
	<li>
		<ul>
			<h4>05.10.2016</h4>
			<li>
				There are four launch modes:
				<dl>
					<dt>standart</dt><dd>New instance instantiated for every activity in current task.</dd>	
					<dt>singleTop</dt>New or existing instance instantiated in current task.<dd></dd>
					<dt>newTask</dt><dd>Instance instantiated in new task.</dd>
					<dt>newInstance</dt><dd>New instantiated creates in new task.</dd>
				</dl>
			</li>
			<li>
				VK official app .apk was dowloaded and was recompiled. I study the structure of the project to 
				create a static page. 
			</li>
		</ul>
	</li>
</ul>
