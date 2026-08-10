### v4.0.1
  - logo/banner file fix for all loader

### v4.0.0
  - internal rework
  - update to 26.1 and 26.2
  - adding:
    - gametests to cover most cases
    - LootDropModifier → Allows modifications to ItemStack after they got generated
  - removing:
    - LootTable classes, everything is available through vanilla code
    - ResourcePackUtils (was never used anywhere)
    - TagUtil