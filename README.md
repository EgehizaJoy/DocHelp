# DocHelp
A personal app to access mental health counselling

# Project setup

Clone the repo, open the project in Android Studio, hit "Run". Done!

# Architecture overview
The app is a stateless client: all operations/mutations are performed
by calling api endpoints over the network.

Local data is in effect immutable, the client just downloads updated
versions of data as needed. Local data is only modified as a result of
api operations.

The domain model objects are used throughout the app. They are plain
Java objects. 

State is stored in a simple DataStore called GlobalState. It loads
from disk on app startup, persists itself to disk as needed in
background. While app is active, all data is kept in memory. The rest
of the app simply puts domain model objects into and out of it without
knowing anything about persistence details.

1.Counsellors availavility refreshes every few seconds
2. User sighn up into the system and after logging in are able to communicate with experts and other users


Activities and fragments are for presentation logic only. Each
activity or fragment should have its own presenter where business
logic is placed. The presenter reacts to data changes via the event
bus, and tells the fragment/activity how to update itself.

## External dependencies

The app does not live in a vacuum. These are the external
APIs/services it depends on in fully rolled out system:

- Firebase for analytics, crash reports and  messaging. All our
  apps share a single Firebase project, see
  
java SDK for programming and managing the background funcctionalities

### Version control workflow
We loosely use the "Git flow" approach: master is the release 
branch - it should always be releasable, and only merged into 
when we have tested and verified that everything works and is 
good to go. 

Daily development is done in the development branch. Features, 
bugfixes and other tasks are done as branches off of develop, 
then merged back into develop directly or via pull requests.

Keep commits atomic and self-explanatory.

## How to test the project
Test: Sign in existing user after a successful sighn in

Test: Sign up new user,after signing up and creating a user account

Test: With signed in user, talk to other users in the platform

Test: With signed in user, access counselling from signed up counselling.

## How to release a new version
- In develop, update the major/minor/patch version, 
increment the version code integer by one. Commit.

- Merge develop into master.

- Tag it:

git tag -a v3.0.19 -m "Fixed a tricky bug"
git push origin --tags

- This should trigger a release hook in Github. 

## Screenshots
<img src="https://raw.githubusercontent.com/EgehizaJoy/DocHelp/master/1.PNG"/>

<img src="https://raw.githubusercontent.com/EgehizaJoy/DocHelp/master/2.PNG"/>



