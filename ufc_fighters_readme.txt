UFC Fighter App Project.
I. Team Members:
1)	Name: Tri Nguyen
	CSID: TTN14
	EID: TVN295
2)	Name: Rain Fang
	CSID: YF365
	EID: YF356

********************************** IMPORTANT NOTE ******************************************
1) Network Activity is required to run this app properly.
2) Eclipse compiliation requires 2 external jars under UFCFighterApp folder to be imported:
	- Parse-1.3.5.jar
	- universal-image-loader-1.8.6.jar
********************************************************************************************

II. General Information.
1) This app is to display/compare UFC MMA Fighters/Events.
2) This project contains 3 sub-projects:
	- UFCFighterApp: the Android app.
	- UFCScraper: backend scripts to populate Parse database on cloud.
	- UFCCloudCode: Parse Cloud code to run jobs on the cloud.

II. Instruction.
1) 2 main activities are from Navigation bar: UFC Events and Fighter Search.
2) The first Activity is a list of upcoming UFC Events.
3) Users can select one of the events and will see a list of fights within that event.
4) A UFC Profile include: fighter picture and fighting record: wins, KO, submission, decision, losses, and titles.
5) From Profile screen, users can select VS to find the fighter to be compared with.
6) The Comparision screen compares 2 fighters by putting their profiles in one screen.

III. Completed Features.
1) List of all upcoming UFC Event dynamically loaded from database.
2) Within each event is list of partipating fighters.
2) List all fighters with a search bar as a filter.
3) Fighter profile loading: fighter information from Parse cloud, and fighter photo from ESPN.
4) Fighter Comparison search as users click VS.
5) Fighters Comparison by stats and fight record.
6) Database is on the Cloud and can be updated regularly.

IV. Desired Features.
1) Full fighter's history record in fighter's profile. Users can view this record by swiping up.
2) Fighter Comparison show relevant fighting history such as: their previous fight, or someone whom they both fought before.
3) Create a Parse Cloud job to refresh and update database regularly, especially event list.
4) Profile display a small graph of recent performance.

V. Improved Features.
1) List of all upcoming UFC Event dynamically loaded from database.
2) List of all fight events within one UFC Event.
3) Navigator bar: UFC Events and Fighter Search.
4) Prettify UI in Profile and Profile Comparision.
5) Database contains all major 542 fighters.
6) Database contains all fight history of all those fighters.

VI. External References.
1) Parse API: for connecting to our Cloud database.
2) Universal Image Loader: for loading fighters' headshot photos asynchronously.
3) Jsoup: for scraping ESPN websites.
4) Gson: Google Json adapter.
5) Common-lang: Text filter and manipulation.
6) ListView search bar filter: androidhive.info/2012/09/android-adding-search-functionality-to-listview/
7) Google Android tutorials for ListView and ArrayAdapter.
8) Tic-tac-toe tutorial: TextView and XML files.
9) Stackoverflow: manipulate XML, AlertDialog, ListViewn and ArrayAdapter.

VII. Implemented Classes.
1) 6 activities: Search, Profile, ComparisonSearch, ComparisonProfile, FightEvent, and UFCEventList.
2) 5 Model classes to represent: Fighter, FightEvent, Fight History, Figher Record, and UFCEvent.
3) Figther Data classess to queries data from Parse and turn into data structure.
4) Scrapers to parse ESPN website and push the info to Parse cloud.

