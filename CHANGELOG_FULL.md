### v3.1.0
- update internal structure
- add back forge

### v3.0.2
- finally fix supported versions, sry it's late**

### v3.0.0
- port to 26.1

### v2.10.0
- update to 1.21.11
- update LottTables

### v2.9.1
- update ResourcePackUtils
    - add methods:
        - readMetaData(MetadataSectionType) - returns the first custom data it finds
        - readAllMetaData(MetadataSectionType) - returns all custom data found in resourcepacks

### v2.9.0
- update to 1.21.9
- add ResourcePackUtils
    - methods:
        - readMetaData(String) - returns the first custom data it finds
        - readAllMetaData(String) - returns all custom data found in resourcepacks

### v2.8.2
- update ResourcePackRegistry

### v2.8.1
- fix neoforge crash

#### v2.8.0
- backport ResourcePackRegistry

#### v2.7.0
- update to 1.21.7
  - works on 1.21.6 and 1.21.7

#### v2.6.1
- Fix Bug where Chests still be empty

#### v2.6.0
- Fixing an incompatibility with CraftTweaker
  - caused empty Chests
- port to 1.21.6

#### v2.5.2
- allow any neo/forge version
- adding an event and mixin to modify ItemStack
  before they get added to a chest or get dropped by an entity

#### v2.5.1
- allow any fabric api/loader version
- make 1.21.1 versions usable on 1.21
- make 1.21.3 versions usable on 1.21.2
- stable version release

#### v2.5.0
- update to 1.21.5
- internal cleanup and unification
- adding a common Event class
  - functions like the Fabric Event System

#### v2.4.7
- fix trial chamber loot tables

#### v2.4.6
- update LootTableModifier to use Supplier instead of fixed values

#### v2.4.5
- update LootTableModifier to be more flexible

#### v2.3.2
- adding ``ResourcePackRegistry``
  - register Built-in resourcepacks from common
- added more methods to ``ItemModelRenderer``
  - ``#registerInHandModel``
  - ``#registerHeadModel``
  - ``#registerGuiModel``
  - ``#registerGroundModel``
  - ``#registerFixedModel``

#### v2.3.0
- Update to 1.21.3
- small rework for ``RegistryProvider``
- small changes on ``LootTableModifier``
  - renamed ``addPool`` to ``lootPool``
- updating loot table helper classes
- renamed ``RecipeRemainder`` to ``ResourceCraftingRemainder``
  - only to reduce the possibility of conflicts with other Mods
- renamed ``ItemModelRenderHelper`` to ``ItemModelRenderer``
  - can now be used in any Loader, so initialize it in common
    <br>and add it to the Client setup in each loader
- renamed ```TagHelper``` to ``TagUtil``
  - creates only ``c`` tags, since Forge introduced the common tags in 1.21.1
- remove ``TreeTrimmingUtil`` -> never used and was buggy

#### v2.0.3
- internal fix with no effect on gameplay

#### v2.0.2
- update CoreServices
- update internal querk

#### v2.0.0
- adding Model Render Helper
- adding Block Model Generator Helper methods
- expand TagHelper

#### v1.3.1
- removing testcode that gets executed sometimes
- porting to forge 1.20.6

#### v1.3.0
- backport update
- fabric 1.20 up to 1.20.4
- forge 1.20.1 up to 1.20.4
- neoforge 1.20.4
- some internal changes for 1.20.5

#### v1.2.1
- adapting new Tag Convention for TagHelper

#### v1.1.0
- adding missing LootTables from the 1.20.5 Update
- fixing Loot Table Abstraction to fit the new ResourceKeys
- making it 1.20.5 version specific

#### v1.0.0
- initial release
- rebranding StophosLib
- added Abstractions
    - Registry
    - Loot Table Modification
- Adding all LootTable Keys
    - for a better overview