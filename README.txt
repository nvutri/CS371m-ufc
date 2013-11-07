UFC Fighter App Project.
I. Team Members:
1)	Name: Tri Nguyen
	CSID: TTN14
	EID: TVN295
2)	Name: Rain Fang
	CSID: YF365
	EID: YF356

II. General Information.
1) This app is to display/compare UFC MMA Fighters/Events.
2) Network Activity is required to run this app properly, because it relies on remote database in Parse cloud.
3) The Parse database is populated using a separate Scraper to fetch information from espn.go.com/mma/ and push to the cloud.

II. Instruction.
1) The first Activity is a list of UFC fighters. Users can select one of the fighters to display his profile.
2) A UFC Profile include: fighter picture and fighting record: wins, KO, submission, decision, losses, and titles.
3) From Profile screen, users can select VS to find the fighter to be compared with.
4) The Comparision screen compares 2 fighters by putting their profiles in one screen.
5) Use Android Back button to return to previous screen

III. Completed Features.
1) List all fighters with a search bar as a filter.
2) Fighter profile loading: fighter information from Parse cloud, and fighter photo from ESPN.
3) Fighter Comparison search as users click VS.
4) Fighters Comparison by stats and fight record.
5) Database is on the Cloud and can be updated regularly.

IV. Desired Features.
1) List of upcoming fighting events and matches as the app begins. Notify users when a match/event is about to happen.
2) Full fighter's history record in fighter's profile. Users can view this record by scrolling down.
3) Fighter Comparison show relevant fighting history such as: their previous fight, or someone whom they both fought before.
4) Create a Parse Cloud job to refresh and update database regularly, especially event list.
5) Profile display a small graph of recent performance.

V. External References.
1) Parse API: for connecting to our Cloud database.
2) Universal Image Loader: for loading fighters' headshot photos asynchronously.
3) Jsoup: for scraping ESPN websites.
4) ListView search bar filter: androidhive.info/2012/09/android-adding-search-functionality-to-listview/
5) Google Android tutorials for ListView and ArrayAdapter.
6) Tic-tac-toe tutorial: TextView and XML files.
7) Stackoverflow: manipulate XML, AlertDialog, ListViewn and ArrayAdapter.

VI. Implemented Classes.
1) 4 activities: Search, Profile, ComparisonSearch, ComparisonProfile.
2) 2 classes to represent: Fighter and Record.
3) Figther Data classess to queries data from Parse and turn into data structure.
4) Scrapers to parse ESPN website and push the info to Parse cloud.

