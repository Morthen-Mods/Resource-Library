### v4.0.0
  - internal rework
  - adding:
    - gametests to cover most cases
    - LootDropModifier → Allows modifications to ItemStack after they got generated
  - removing:
    - LootTable classes, everything is available through vanilla code
    - ResourcePackUtils (was never used anywhere)
    - TagUtil