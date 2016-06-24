# TS3Bot #
TS3 ServerQuery Bot, based on [JTS3ServerQuery Library by Stefan1200](https://www.stefan1200.de/forum/index.php)

## TODO ##
- Add dynamic system for Events
- Finish the settings system
- Add all events
- (Maybe add WebInterface)

## Basic Commands ##
### Global: ###
- ![botname]
	Calls the bot to your help!

### Private: ###
- ?
	List of commands (As regex pattern for now)
- shutdown
	Disabled at the moment, have to work out some kind of permissions system
- execute
	Disabled at the moment, have to work out some kind of permissions system

## Core Plugins ##
- KeepAlive (Sends keep alive to make sure the bot stays connected to the server)
- AFKMover (Moves people to the afk channel after 10 minutes of inactivity and back when they come back active. Disabled on per user basis by "afk off". Enabled by "afk on")
- RollTheDice (Only implements the "coin" private command for now)

Bundled as Core.jar
