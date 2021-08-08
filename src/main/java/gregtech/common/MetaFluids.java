package gregtech.common;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.MaterialRegistry;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.material.properties.FluidProperty;
import gregtech.api.unification.material.properties.PlasmaProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.util.FluidTooltipUtil;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class MetaFluids {

    private static final Set<ResourceLocation> fluidSprites = new HashSet<>();
    private static final Map<String, Material> fluidToMaterialMappings = new HashMap<>();
    private static final Map<String, String> alternativeFluidNames = new HashMap<>();

    public static final ResourceLocation AUTO_GENERATED_PLASMA_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/fluids/fluid.plasma.autogenerated");
    public static final ResourceLocation FLUID_BRIGHT_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/bright/fluid");
    public static final ResourceLocation FLUID_DIAMOND_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/diamond/fluid");
    public static final ResourceLocation FLUID_DULL_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/dull/fluid");
    public static final ResourceLocation FLUID_EMERALD_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/emerald/fluid");
    public static final ResourceLocation FLUID_FINE_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/fine/fluid");
    public static final ResourceLocation FLUID_FLINT_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/flint/fluid");
    public static final ResourceLocation FLUID_MOLTEN_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/fluid/fluid");
    public static final ResourceLocation FLUID_GAS_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/gas/fluid");
    public static final ResourceLocation FLUID_GEM_HORIZONTAL_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/gem_horizontal/fluid");
    public static final ResourceLocation FLUID_GEM_VERTICAL_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/gem_vertical/fluid");
    public static final ResourceLocation FLUID_GLASS_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/glass/fluid");
    public static final ResourceLocation FLUID_LAPIS_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/lapis/fluid");
    public static final ResourceLocation FLUID_LIGNITE_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/lignite/fluid");
    public static final ResourceLocation FLUID_MAGNETIC_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/magnetic/fluid");
    public static final ResourceLocation FLUID_METALLIC_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/metallic/fluid");
    public static final ResourceLocation FLUID_NETHERSTAR_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/netherstar/fluid");
    public static final ResourceLocation FLUID_NONE_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/none/fluid");
    public static final ResourceLocation FLUID_OPAL_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/opal/fluid");
    public static final ResourceLocation FLUID_PAPER_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/paper/fluid");
    public static final ResourceLocation FLUID_POWDER_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/powder/fluid");
    public static final ResourceLocation FLUID_QUARTZ_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/quartz/fluid");
    public static final ResourceLocation FLUID_ROUGH_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/rough/fluid");
    public static final ResourceLocation FLUID_RUBY_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/ruby/fluid");
    public static final ResourceLocation FLUID_SAND_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/sand/fluid");
    public static final ResourceLocation FLUID_SHARDS_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/shards/fluid");
    public static final ResourceLocation FLUID_SHINY_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/shiny/fluid");
    public static final ResourceLocation FLUID_WOOD_TEXTURE = new ResourceLocation(
            GTValues.MODID, "blocks/material_sets/wood/fluid");

    private static final Map<Pair<Material, FluidType>, ResourceLocation> fluidTextureMap = new HashMap<>();

    public enum FluidType {
        NORMAL("", material -> material.hasFluid() && material.getProperty(PropertyKey.FLUID).isGas() ? FluidState.GAS : FluidState.LIQUID),
        PLASMA("plasma.", material -> FluidState.PLASMA);

        private final String prefix;
        private final Function<Material, FluidState> stateFunction;

        FluidType(String prefix, Function<Material, FluidState> stateFunction) {
            this.prefix = prefix;
            this.stateFunction = stateFunction;
        }

        public String getFluidName(Material material) {
            return prefix + material.toString();
        }

        public FluidState getFluidState(Material material) {
            return stateFunction.apply(material);
        }
    }

    public enum FluidState {
        LIQUID(null),
        GAS(null),
        PLASMA("gregtech.fluid.plasma");

        private final String prefixLocale;

        FluidState(String prefixLocale) {
            this.prefixLocale = prefixLocale;
        }
    }

    public static void registerSprites(TextureMap textureMap) {
        for (ResourceLocation spriteLocation : fluidSprites) {
            textureMap.registerSprite(spriteLocation);
        }
    }

    public static void init() {
        /**
         * Every {@link gregtech.api.unification.material.info.MaterialIconSet} that has fluids requires a registered sprite
         */
        fluidSprites.add(AUTO_GENERATED_PLASMA_TEXTURE);
        fluidSprites.add(FLUID_BRIGHT_TEXTURE);
        fluidSprites.add(FLUID_DIAMOND_TEXTURE);
        fluidSprites.add(FLUID_DULL_TEXTURE);
        fluidSprites.add(FLUID_EMERALD_TEXTURE);
        fluidSprites.add(FLUID_FINE_TEXTURE);
        fluidSprites.add(FLUID_FLINT_TEXTURE);
        fluidSprites.add(FLUID_MOLTEN_TEXTURE);
        fluidSprites.add(FLUID_GAS_TEXTURE);
        fluidSprites.add(FLUID_GEM_HORIZONTAL_TEXTURE);
        fluidSprites.add(FLUID_GEM_VERTICAL_TEXTURE);
        fluidSprites.add(FLUID_GLASS_TEXTURE);
        fluidSprites.add(FLUID_LAPIS_TEXTURE);
        fluidSprites.add(FLUID_LIGNITE_TEXTURE);
        fluidSprites.add(FLUID_MAGNETIC_TEXTURE);
        fluidSprites.add(FLUID_METALLIC_TEXTURE);
        fluidSprites.add(FLUID_NETHERSTAR_TEXTURE);
        fluidSprites.add(FLUID_NONE_TEXTURE);
        fluidSprites.add(FLUID_OPAL_TEXTURE);
        fluidSprites.add(FLUID_PAPER_TEXTURE);
        fluidSprites.add(FLUID_POWDER_TEXTURE);
        fluidSprites.add(FLUID_QUARTZ_TEXTURE);
        fluidSprites.add(FLUID_ROUGH_TEXTURE);
        fluidSprites.add(FLUID_RUBY_TEXTURE);
        fluidSprites.add(FLUID_SAND_TEXTURE);
        fluidSprites.add(FLUID_SHARDS_TEXTURE);
        fluidSprites.add(FLUID_SHINY_TEXTURE);
        fluidSprites.add(FLUID_WOOD_TEXTURE);

        // handle vanilla fluids
        Materials.Water.getProperty(PropertyKey.FLUID).setFluid(FluidRegistry.WATER);
        Materials.Lava.getProperty(PropertyKey.FLUID).setFluid(FluidRegistry.LAVA);

        //alternative names for forestry fluids
        setAlternativeFluidName(Materials.Ethanol, FluidType.NORMAL, "bio.ethanol");
        setAlternativeFluidName(Materials.Honey, FluidType.NORMAL, "for.honey");
        setAlternativeFluidName(Materials.SeedOil, FluidType.NORMAL, "seed.oil");
        setAlternativeFluidName(Materials.Ice, FluidType.NORMAL, "fluid.ice");
        setAlternativeFluidName(Materials.Diesel, FluidType.NORMAL, "fuel");

        setMaterialFluidTexture(Materials.Air, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Deuterium, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.DistilledWater, FluidType.NORMAL, new ResourceLocation("blocks/water_still"));
        setMaterialFluidTexture(Materials.Tritium, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Helium, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Helium, FluidType.PLASMA);
        setMaterialFluidTexture(Materials.Helium3, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Fluorine, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.TitaniumTetrachloride, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Steam, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.OilHeavy, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.OilMedium, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.OilLight, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.HydrogenSulfide, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.SulfuricGas, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.RefineryGas, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.SulfuricNaphtha, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.SulfuricLightFuel, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.SulfuricHeavyFuel, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Naphtha, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.LightFuel, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.HeavyFuel, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.LPG, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.SteamCrackedLightFuel, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.SteamCrackedHeavyFuel, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Chlorine, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.NitroDiesel, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.SodiumPersulfate, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.GlycerylTrinitrate, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Lubricant, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Creosote, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.SeedOil, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Oil, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Diesel, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Honey, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Biomass, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Ethanol, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.SulfuricAcid, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Milk, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.McGuffium239, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Glue, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.HydrochloricAcid, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.LeadZincSolution, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.NaturalGas, FluidType.NORMAL);
        setMaterialFluidTexture(Materials.Blaze, FluidType.NORMAL);

        for (Material material : MaterialRegistry.MATERIAL_REGISTRY) {
            FluidProperty fluidProperty = material.getProperty(PropertyKey.FLUID);
            if (fluidProperty == null) continue;

            if (fluidProperty.getFluid() == null) {
                int temperature = fluidProperty.getFluidTemperature();
                Fluid fluid = registerFluid(material, FluidType.NORMAL, temperature, fluidProperty.hasBlock());
                fluidProperty.setFluid(fluid);
                FluidTooltipUtil.registerTooltip(fluid, material.getChemicalFormula());
            }

            PlasmaProperty plasmaProperty = material.getProperty(PropertyKey.PLASMA);
            if (plasmaProperty != null && plasmaProperty.getPlasma() == null) {
                Fluid fluid = registerFluid(material, FluidType.PLASMA, fluidProperty.getFluidTemperature() + 30000, false);
                plasmaProperty.setPlasma(fluid);
                FluidTooltipUtil.registerTooltip(fluid, material.getChemicalFormula());
            }
        }
    }

    /**
     * Changes the texture of specified material's fluid.
     * The material color is overlayed on top of the texture, set the materialRGB to 0xFFFFFF to remove the overlay.
     *
     * @param material  the material whose texture to change
     * @param fluidType the type of the fluid
     */
    public static void setMaterialFluidTexture(Material material, FluidType fluidType) {
        String path = "blocks/fluids/fluid." + material.toString();
        if (fluidType.equals(FluidType.PLASMA))
            path += ".plasma";
        ResourceLocation resourceLocation = new ResourceLocation(GTValues.MODID, path);
        setMaterialFluidTexture(material, fluidType, resourceLocation);
    }

    /**
     * Changes the texture of specified material's fluid.
     * The material color is overlayed on top of the texture, set the materialRGB to 0xFFFFFF to remove the overlay.
     *
     * @param material        the material whose texture to change
     * @param fluidType       the type of the fluid
     * @param textureLocation the location of the texture to use
     */
    public static void setMaterialFluidTexture(Material material, FluidType fluidType, ResourceLocation textureLocation) {
        fluidTextureMap.put(Pair.of(material, fluidType), textureLocation);
        fluidSprites.add(textureLocation);
    }

    public static void setAlternativeFluidName(Material material, FluidType fluidType, String alternativeName) {
        alternativeFluidNames.put(fluidType.getFluidName(material), alternativeName);
    }

    public static Fluid registerFluid(Material material, FluidType fluidType, int temperature, boolean generateBlock) {
        String materialName = material.toString();
        String fluidName = fluidType.getFluidName(material);
        Fluid fluid = FluidRegistry.getFluid(fluidName);

        // check if fluid is registered from elsewhere under an alternative name
        if (fluid == null && alternativeFluidNames.containsKey(fluidName)) {
            String altName = alternativeFluidNames.get(fluidName);
            fluid = FluidRegistry.getFluid(altName);
        }

        // if the material is still not registered by this point, register it
        if (fluid == null) {
            FluidState fluidState = fluidType.getFluidState(material);

            // determine texture for use
            ResourceLocation textureLocation;
            if (fluidType.equals(FluidType.PLASMA)) // use a custom texture if it exists, else use the universal plasma texture for plasmas
                textureLocation = fluidTextureMap.getOrDefault(Pair.of(material, fluidType), AUTO_GENERATED_PLASMA_TEXTURE);
            else // use a custom texture if it exists, else use the material's iconset fluid texture
                textureLocation = fluidTextureMap.getOrDefault(Pair.of(material, fluidType), MaterialIconType.fluid.getBlockPath(material.getMaterialIconSet()));


            fluid = new MaterialFluid(fluidName, material, fluidState, textureLocation);
            fluid.setTemperature(temperature);
            fluid.setColor(GTUtility.convertRGBtoOpaqueRGBA_MC(material.getMaterialRGB()));

            // set properties and register
            setStateProperties(fluid, fluidState);
            FluidRegistry.registerFluid(fluid);
        }

        // add buckets for each fluid
        FluidRegistry.addBucketForFluid(fluid);

        // generate fluid blocks if the material has one, and the current state being handled is not plasma
        if (generateBlock && fluid.getBlock() == null && fluidType != FluidType.PLASMA) {
            BlockFluidBase fluidBlock = new BlockFluidClassic(fluid, net.minecraft.block.material.Material.WATER);
            fluidBlock.setRegistryName("fluid." + materialName);
            MetaBlocks.FLUID_BLOCKS.add(fluidBlock);
        }

        fluidToMaterialMappings.put(fluid.getName(), material);
        return fluid;
    }

    public static Material getMaterialFromFluid(Fluid fluid) {
        Material material = fluidToMaterialMappings.get(fluid.getName());
        if (material.hasFluid())
            return material;
        return null;
    }

    public static void setStateProperties(Fluid fluid, FluidState fluidState) {
        switch (fluidState) {
            case LIQUID:
                fluid.setGaseous(false);
                fluid.setViscosity(1000);
                break;
            case GAS:
                fluid.setGaseous(true);
                fluid.setDensity(-100);
                fluid.setViscosity(200);
                break;
            case PLASMA:
                fluid.setGaseous(true);
                fluid.setDensity(55536);
                fluid.setViscosity(10);
                fluid.setLuminosity(15);
                break;
        }
    }

    private static class MaterialFluid extends Fluid {

        private final Material material;
        private final FluidState state;

        public MaterialFluid(String fluidName, Material material, FluidState fluidState, ResourceLocation texture) {
            super(fluidName, texture, texture, GTUtility.convertRGBtoOpaqueRGBA_MC(material.getMaterialRGB()));
            this.material = material;
            this.state = fluidState;
        }

        @Override
        public String getUnlocalizedName() {
            return material.getUnlocalizedName();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getLocalizedName(FluidStack stack) {
            String localizedName = I18n.format(getUnlocalizedName());
            if (state.prefixLocale != null) {
                return I18n.format(state.prefixLocale, localizedName);
            }
            return localizedName;
        }
    }

}
