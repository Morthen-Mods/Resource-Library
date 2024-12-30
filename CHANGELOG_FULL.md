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