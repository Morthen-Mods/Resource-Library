#### v2.3.0
- small rework for RegistryProvider
  - only accepts registries and no longer ResourceKeys
- small changes on LootTableModifier
  - ``addPool`` --> ``lootPool``
- renamed ``RecipeRemainde`` --> ``ResourceCraftingRemainder``
  - only to reduce the possibility of conflicts with other Mods
- remove TreeTrimmingUtil -> never used and was buggy
- remove TagUtil -> Forge also introduced ``c:`` Tags
- 