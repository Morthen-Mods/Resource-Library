### v5.0.0
- **breaking:** reworked registration API
  - `RegistryObject<R, T>` now has the registry type and the object type, so `getResourceKey()` returns a `ResourceKey<R>` (e.g. `ResourceKey<Item>`)
  - `RegistryObject` adds `getHolder()`, `isBound()`, `equals`/`hashCode`/`toString` based on the key
  - `RegistryProvider.get(...)` was replaced by `RegistryProvider.create(modId, Registry)` and `RegistryProvider.create(modId, ResourceKey)`
  - `RegistryProvider.register(id, key -> ...)` passes the `ResourceKey` to the factory, e.g. for `Item.Properties#setId`
  - `RegistryProvider.getEntries()` is unmodifiable
- **breaking:** `LootTableModifier.addItem(...)` takes a `Supplier<? extends ItemLike>` instead of a `RegistryObject`
- adding common networking (`Networking`): payloads are registered once in common code for all loaders
