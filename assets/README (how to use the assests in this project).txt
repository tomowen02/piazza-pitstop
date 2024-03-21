This project uses texture packing to make loading more efficient.

Use the LibGDX texture packer GUI tool: https://github.com/crashinvaders/gdx-texture-packer-gui

This tool generates a texture atlas (you do not need to edit this manually)

When the textures are in a packed png, the originals are no longer needed. However I have kept them in the 
project files just in case you need to repack them.

There is a GDX Texture Packer project file that you can open in the assets file (called texturePackProject.tpproj)
to edit the texture pack


### Maps ###
Use the Tiled map editor to create maps for the game.
Create a 'Collisions' object layer and use rectangles to define collision areas.
Create a 'Triggers' object layer and use rectangles to define interactable triggers.

Use custom properties on triggers to define game behaviour.

Currently supported triggers:
activity : string - Name of activity
*name of activity* : int - Value assigned to activity (e.g. score)
energy_cost : int - Amount of energy consumed when interacting with trigger
score : int - Increase score
new_map : string - Path of new map to switch to (root directory = assets/Maps)
new_map_x : int - X position of the player on the new map
new_map_y : int - Y position of the player on the new map
sleep : bool - If the player can sleep here
success_message : string - Display this message if the interaction succeeds
failed_message : string - Display this message if the interaction fails