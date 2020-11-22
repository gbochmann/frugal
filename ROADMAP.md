# Roadmap
## Automatic categorisation
As a shopper, I want my list to be categorised (using colors) by items that I frequently buy together.

### Implementation
By remembering which items frequently co-occur I'm going to create create vectors of items that occur together at least two or three times. These vectors are going to be categories. Based on category membership, a color will be randomly assigned.

## Automatic sorting
As a shopper. I want my list to be sorted by items that I frequently buy together.

### Implementation
Using the last item for each item, a list will be created that counts for each neighbour how frequently it occurs with the current shopping item.

## Edit mode and cart mode
As a shopper, I want a UI for editing the shopping list and a UI for adding items to my cart.

Edit mode will be used for editing the shopping list and cart mode for crossing off items and adding them to the cart. The cart will later be used to calculate the price.

## Bar code scanner

## Minor fixes

- It's possible to add items with empty labels, consider using specs to prevent that
