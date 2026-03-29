-- Roblox Clicker Game Script (Best placed in ServerScriptService)
-- This script handles Leaderstats, Clicking, and a basic Shop system.

game.Players.PlayerAdded:Connect(function(player)
    -- 1. Create Leaderstats (Points/Money displayed in the top right)
    local leaderstats = Instance.new("Folder")
    leaderstats.Name = "leaderstats"
    leaderstats.Parent = player

    local clicks = Instance.new("IntValue")
    clicks.Name = "Clicks"
    clicks.Value = 0
    clicks.Parent = leaderstats

    local coins = Instance.new("IntValue")
    coins.Name = "Coins"
    coins.Value = 0
    coins.Parent = leaderstats

    -- 2. Create and Give Clicker Tool
    -- This allows players to click anywhere to gain points
    local function giveTool(targetBackpack)
        local tool = Instance.new("Tool")
        tool.Name = "Clicker"
        tool.RequiresHandle = false -- No physical handle needed

        tool.Activated:Connect(function()
            clicks.Value = clicks.Value + 1
            -- Bonus coins every 10 clicks
            if clicks.Value % 10 == 0 then
                coins.Value = coins.Value + 5
            else
                coins.Value = coins.Value + 1
            end
        end)

        tool.Parent = targetBackpack
    end

    -- Initial give
    giveTool(player.Backpack)

    -- Give tool again if the player respawns
    player.CharacterAdded:Connect(function()
        giveTool(player.Backpack)
    end)
end)

-- 3. Create a Simple Shop Part in the World
-- Stepping on this part exchanges Clicks for special rewards
local shopPart = Instance.new("Part")
shopPart.Name = "ExchangeStation"
shopPart.Size = Vector3.new(10, 1, 10)
shopPart.Position = Vector3.new(0, 0.5, 20) -- Positioned a bit away from spawn
shopPart.Anchored = true
shopPart.BrickColor = BrickColor.new("Bright green")
shopPart.Parent = game.Workspace

-- Add a label so players know what it does
local surfaceGui = Instance.new("SurfaceGui")
surfaceGui.Face = Enum.NormalId.Top
surfaceGui.Parent = shopPart

local textLabel = Instance.new("TextLabel")
textLabel.Size = UDim2.new(1, 0, 1, 0)
textLabel.BackgroundTransparency = 1
textLabel.Text = "Step here to trade 100 Clicks for 50 extra Coins!"
textLabel.TextColor3 = Color3.new(1, 1, 1)
textLabel.TextScaled = true
textLabel.Parent = surfaceGui

-- Debounce to prevent rapid multiple triggers
local db = false
shopPart.Touched:Connect(function(hit)
    if db then return end

    local character = hit.Parent
    local player = game.Players:GetPlayerFromCharacter(character)

    if player then
        db = true
        local clicks = player.leaderstats.Clicks
        local coins = player.leaderstats.Coins

        if clicks.Value >= 100 then
            clicks.Value = clicks.Value - 100
            coins.Value = coins.Value + 50
            print(player.Name .. " traded Clicks for Coins!")

            -- Visual feedback
            shopPart.BrickColor = BrickColor.new("Bright yellow")
            task.wait(0.5)
            shopPart.BrickColor = BrickColor.new("Bright green")
        else
            -- Not enough clicks
            shopPart.BrickColor = BrickColor.new("Bright red")
            task.wait(0.5)
            shopPart.BrickColor = BrickColor.new("Bright green")
        end
        db = false
    end
end)

print("Roblox Clicker Game Script Loaded!")
