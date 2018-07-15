package it.ostpol.furniture.util.jei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.ostpol.furniture.init.ModBlocks;
import it.ostpol.furniture.init.ModItems;
import it.ostpol.furniture.util.jei.food.FoodRecipe;
import it.ostpol.furniture.util.jei.freezer.FreezerRecipe;
import it.ostpol.furniture.util.jei.grinder.GrinderRecipe;
import it.ostpol.furniture.util.jei.interact.InteractRecipe;
import it.ostpol.furniture.util.jei.sink.SinkRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeHandler {

	public static final List<FoodRecipe> FOOD_PAN_LIST = new ArrayList<FoodRecipe>();
	public static final List<FoodRecipe> FOOD_OVEN_LIST = new ArrayList<FoodRecipe>();
	public static final List<FoodRecipe> FOOD_DEEP_FRY_LIST = new ArrayList<FoodRecipe>();
	public static final List<FoodRecipe> FOOD_MICROWAVE_LIST = new ArrayList<FoodRecipe>();
	public static final List<SinkRecipe> SINK_RECIPE_LIST = new ArrayList<SinkRecipe>();
	public static final List<FreezerRecipe> FREEZER_RECIPE_LIST = new ArrayList<FreezerRecipe>();
	public static final List<GrinderRecipe> GRINDER_RECIPE_LIST = new ArrayList<GrinderRecipe>();
	public static final List<InteractRecipe> INTERACT_RECIPE_LIST = new ArrayList<InteractRecipe>();
	
	/**
	 * DON'T USE THIS MAP. Use addFreezerRecipe
	 */
	public static final Map<Item, Item> FREEZER_RECIPES = new HashMap<Item, Item>();
	
	
	public static void init() {
		SINK_RECIPE_LIST.add(new SinkRecipe(new ItemStack(Items.BUCKET), new ItemStack(Items.WATER_BUCKET)));
		SINK_RECIPE_LIST.add(new SinkRecipe(new ItemStack(Items.GLASS_BOTTLE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER)));
	
		FREEZER_RECIPE_LIST.add(new FreezerRecipe(new ItemStack(Items.WATER_BUCKET), new ItemStack(Blocks.ICE)));
		FREEZER_RECIPE_LIST.add(new FreezerRecipe(new ItemStack(Items.LAVA_BUCKET), new ItemStack(ModItems.MAGMA_BUCKET)));
		FREEZER_RECIPE_LIST.add(new FreezerRecipe(new ItemStack(ModItems.MAGMA_BUCKET), new ItemStack(ModItems.OBSIDIAN_BUCKET)));
		FREEZER_RECIPE_LIST.add(new FreezerRecipe(new ItemStack(Blocks.ICE), new ItemStack(Blocks.PACKED_ICE)));
		
		
		GRINDER_RECIPE_LIST.add(new GrinderRecipe(new ItemStack(Items.WHEAT), new ItemStack(ModItems.FLOUR)));
		GRINDER_RECIPE_LIST.add(new GrinderRecipe(new ItemStack(ModItems.BREAD_ROLL), new ItemStack(ModItems.BREAD_CRUMBS))); 
		
		addFryerRecipe(new ItemStack(ModItems.PANEED_BEEF), new ItemStack(ModItems.SCHNITZEL));
		addPanRecipe(new ItemStack(ModItems.PANEED_BEEF), new ItemStack(ModItems.SCHNITZEL));
		
		for (FreezerRecipe f : FREEZER_RECIPE_LIST) {
			FREEZER_RECIPES.put(f.in.getItem(), f.out.getItem());
		}
		
		addOvenRecipe(new ItemStack(ModItems.DOUGH), new ItemStack(ModItems.BREAD_ROLL));
		
		addInteractRecipe(ItemStack.EMPTY, new ItemStack(ModBlocks.TOILET_PAPER_HOLDER), new ItemStack(ModItems.TOILET_PAPER));
	}
	
	/**
	 * Used to add Oven and Grinder recipes from the FurnaceRecipe List after the have been added by other mods
	 */
	public static void postInit() {
		for (ItemStack stack : FurnaceRecipes.instance().getSmeltingList().keySet()) {
			if (stack.getItem() instanceof ItemFood) {
				FOOD_PAN_LIST.add(new FoodRecipe(stack, FurnaceRecipes.instance().getSmeltingResult(stack)));
				FOOD_OVEN_LIST.add(new FoodRecipe(stack, FurnaceRecipes.instance().getSmeltingResult(stack)));
				FOOD_DEEP_FRY_LIST.add(new FoodRecipe(stack, FurnaceRecipes.instance().getSmeltingResult(stack)));
				FOOD_MICROWAVE_LIST.add(new FoodRecipe(stack, FurnaceRecipes.instance().getSmeltingResult(stack)));
			}
		}
		
		for (ItemStack s : OreDictionary.getOres("cropCoffee")) {
			GRINDER_RECIPE_LIST.add(new GrinderRecipe(s.copy(), new ItemStack(ModItems.COFFEE_POWDER)));
		}
	}
	/**
	 * Check if an Item exists in a Food Recipe List. The food recipe Lists are: FOOD_PAN_LIST, FOOD_OVEN_LIST, FOOD_DEEP_FRY_LIST, FOOD_MICROWAVE_LIST
	 * @param l A Food recipe List
	 * @param i Item to Check
	 * @return If the Item was found in the list
	 */
	public static boolean isInFoodRecipe(List<FoodRecipe> l, Item i) {
		for (FoodRecipe r : l) {
			if (r.in.getItem().equals(i))
				return true;
		}
		return false;
	}

	
	/**
	 * Add a Recipe to any Oven
	 * @param in Input Item Stack
	 * @param out Output Item Stack
	 */
	public static void addOvenRecipe(ItemStack in, ItemStack out) {
		FOOD_OVEN_LIST.add(new FoodRecipe(in, out));
	}
	
	/**
	 * Add a Recipe to the Pan
	 * @param in Input Item Stack
	 * @param out Output Item Stack
	 */
	public static void addPanRecipe(ItemStack in, ItemStack out) {
		FOOD_PAN_LIST.add(new FoodRecipe(in, out));
	}
	
	/**
	 * Add a Recipe to the counter with fryer
	 * @param in Input ItemStack
	 * @param out Output ItemStack
	 */
	public static void addFryerRecipe(ItemStack in, ItemStack out) {
		FOOD_DEEP_FRY_LIST.add(new FoodRecipe(in, out));
	}
	
	/**
	 * Currently not used!
	 * @param in
	 * @param out
	 */
	@Deprecated
	public static void addMicrowaveRecipe(ItemStack in, ItemStack out) {
		FOOD_MICROWAVE_LIST.add(new FoodRecipe(in, out));
	}
	
	/**
	 * Add a Recipe to the Grinder
	 * @param in Input ItemStack
	 * @param out Output ItemStack
	 */
	public static void addGrinderRecipe(ItemStack in, ItemStack out) {
		GRINDER_RECIPE_LIST.add(new GrinderRecipe(in, out));
	}
	
	/**
	 * Add a Recipe to the Freezer
	 * @param in Input ItemStack
	 * @param out Output ItemStack
	 */
	public static void addFreezerRecipe(ItemStack in, ItemStack out) {
		FREEZER_RECIPE_LIST.add(new FreezerRecipe(in, out));
		FREEZER_RECIPES.put(in.getItem(), out.getItem());
	}
	
	/**
	 * Add a Recipe to World Interaction. This is for JEI Display Only. This does not add
	 * new Functionality to Blocks
	 * @param in What to Hold in Hand
	 * @param on What to Interact with
	 * @param out Output
	 * @param sneak If Player has to sneak to Perform Action
	 */
	public static void addInteractRecipe(ItemStack in, ItemStack on, ItemStack out) {
		INTERACT_RECIPE_LIST.add(new InteractRecipe(in, on, out));
	}
	
	/**
	 * Check if an ItemStack is in the Recipe list for the Grinder
	 * @param stack ItemStack to check
	 * @return
	 */
	public static boolean isGrinderRecipe(ItemStack stack) {
		for (GrinderRecipe r : GRINDER_RECIPE_LIST) {
			if (r.in.getItem().equals(stack.getItem()))
				return true;
		}
		return false;
	}
	
	/**
	 * Check if an ItemStack is in the Recipe list for the Sink
	 * @param stack ItemStack to check
	 * @return
	 */
	public static boolean isSinkRecipe(ItemStack stack) {
		for (SinkRecipe r : SINK_RECIPE_LIST)
			if (r.in.getItem().equals(stack.getItem()))
				return true;
		return false;
	}
	
	/**
	 * If world interaction is Valid / Registered
	 * @param in Player's Held ItemStack
	 * @param on Interacted Block
	 * @return If Recipe is Registered
	 */
	public static boolean isInteractRecipe(ItemStack in, ItemStack on) {
		for (InteractRecipe r : INTERACT_RECIPE_LIST)
			if (ItemStack.areItemsEqual(r.in, in) && ItemStack.areItemsEqual(r.on, on))
				return true;
		return false;
	}
	
	/**
	 * Get sink output for ItemStack
	 * @param stack 
	 * @return Output ItemStack. Returns ItemStack.EMPTY ItemStack if nothing found
	 */
	public static ItemStack getSinkOutput(ItemStack stack) {
		for (SinkRecipe r : SINK_RECIPE_LIST)
			if (r.in.getItem().equals(stack.getItem()))
				return r.out.copy();
		return ItemStack.EMPTY;
	}
	
	/**
	 * Get grinder output for ItemStack
	 * @param stack
	 * @return Output ItemStack. Returns ItemStack.EMPTY ItemStack if nothing found
	 */
	public static ItemStack getOutputGrinder(ItemStack stack) {
		for (GrinderRecipe r : GRINDER_RECIPE_LIST) {
			if (r.in.getItem().equals(stack.getItem()))
				return r.out.copy();
		}
		return ItemStack.EMPTY;
	}
	
}

