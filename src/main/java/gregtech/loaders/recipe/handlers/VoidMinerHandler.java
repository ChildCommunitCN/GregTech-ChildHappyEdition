package gregtech.loaders.recipe.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;

public class VoidMinerHandler {

    public static void init() {
        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
            if (material.hasProperty(PropertyKey.ORE)) {
                RecipeMaps.VOID_MINER.recipeBuilder()
                        .EUt(1).duration(1)
                        .notConsumable(OrePrefix.ore, material)
                        .output(OrePrefix.ore, material, 256)
                        .buildAndRegister();
            }
        }
    }
}
