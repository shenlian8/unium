-- Roblox All-In-One Clicker Simulator Script (FIXED VERSION)
-- Place this in ServerScriptService (NOT Workspace, NOT StarterGui)

local DataStoreService = game:GetService("DataStoreService")
local playerDataStore = DataStoreService:GetDataStore("PlayerData_v3") -- New version
local Players = game:GetService("Players")

-- Configuration
local PET_LIST = {
    {Name = "Cat", Multiplier = 1.5, Chance = 50, Color = BrickColor.new("White")},
    {Name = "Dog", Multiplier = 2.0, Chance = 30, Color = BrickColor.new("Brown")},
    {Name = "Dragon", Multiplier = 5.0, Chance = 15, Color = BrickColor.new("Bright red")},
    {Name = "Godly", Multiplier = 20.0, Chance = 5, Color = BrickColor.new("Bright yellow")}
}

local WORLD_UNLOCK_COST = 5000
local EXCHANGE_CLICKS_COST = 100
local EXCHANGE_COINS_REWARD = 50

print("--- [SIMULATOR SCRIPT LOADING] ---")

-- Helper: Calculate Multiplier
local function calculateMultiplier(player)
    local multiplier = 1
    local petsFolder = player:FindFirstChild("Pets")
    if petsFolder then
        for _, pet in ipairs(petsFolder:GetChildren()) do
            multiplier = multiplier + (pet:GetAttribute("Multiplier") or 0)
        end
    end
    return multiplier
end

-- MAIN PLAYER SETUP FUNCTION
local function onPlayerAdded(player)
    print("Player joined: " .. player.Name)

    -- 1. Create Leaderstats (Must be named exactly "leaderstats")
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

    local unlockedWorld = Instance.new("BoolValue")
    unlockedWorld.Name = "UnlockedWorld2"
    unlockedWorld.Value = false
    unlockedWorld.Parent = player

    -- 2. Pets Folder
    local petsFolder = Instance.new("Folder")
    petsFolder.Name = "Pets"
    petsFolder.Parent = player

    -- 3. Load Data
    local success, data = pcall(function()
        return playerDataStore:GetAsync(tostring(player.UserId))
    end)

    if success and data then
        print("Data loaded for " .. player.Name)
        clicks.Value = data.Clicks or 0
        coins.Value = data.Coins or 0
        unlockedWorld.Value = data.UnlockedWorld2 or false
        if data.Pets then
            for _, petName in ipairs(data.Pets) do
                for _, petInfo in ipairs(PET_LIST) do
                    if petInfo.Name == petName then
                        local p = Instance.new("StringValue")
                        p.Name = petInfo.Name
                        p:SetAttribute("Multiplier", petInfo.Multiplier)
                        p.Parent = petsFolder
                        break
                    end
                end
            end
        end
    else
        warn("No data found or load failed for " .. player.Name)
    end

    -- 4. Tool Giving Logic
    local function giveTool()
        -- Wait for backpack to exist
        local backpack = player:WaitForChild("Backpack", 5)
        if not backpack then return end

        -- Remove old clicker if it exists
        local old = backpack:FindFirstChild("Clicker") or (player.Character and player.Character:FindFirstChild("Clicker"))
        if old then old:Destroy() end

        local tool = Instance.new("Tool")
        tool.Name = "Clicker"
        tool.RequiresHandle = false

        tool.Activated:Connect(function()
            local mult = calculateMultiplier(player)
            clicks.Value = clicks.Value + (1 * mult)
            -- Passive coin gain (guaranteed at least 1)
            coins.Value = coins.Value + math.max(1, math.floor(0.5 * mult))
        end)

        tool.Parent = backpack
        print("Clicker tool given to " .. player.Name)
    end

    -- Initial give and give on respawn
    giveTool()
    player.CharacterAdded:Connect(function()
        task.wait(1) -- Short wait to ensure character is ready
        giveTool()
    end)
end

-- Connect to existing and new players
Players.PlayerAdded:Connect(onPlayerAdded)
for _, player in ipairs(Players:GetPlayers()) do
    onPlayerAdded(player)
end

-- SAVE DATA
Players.PlayerRemoving:Connect(function(player)
    local petNames = {}
    if player:FindFirstChild("Pets") then
        for _, pet in ipairs(player.Pets:GetChildren()) do
            table.insert(petNames, pet.Name)
        end
    end

    local data = {
        Clicks = player.leaderstats.Clicks.Value,
        Coins = player.leaderstats.Coins.Value,
        UnlockedWorld2 = player.UnlockedWorld2.Value,
        Pets = petNames
    }

    local success, err = pcall(function()
        playerDataStore:SetAsync(tostring(player.UserId), data)
    end)
    if success then print("Saved data for " .. player.Name) else warn("Save failed: " .. err) end
end)

-- 5. WORLD STATIONS (Exchange, Gacha, Gate)
-- Create a helper for stations
local function createStation(name, pos, size, color, text)
    local part = Instance.new("Part")
    part.Name = name
    part.Position = pos
    part.Size = size
    part.Anchored = true
    part.BrickColor = color
    part.Parent = game.Workspace

    local gui = Instance.new("SurfaceGui")
    gui.Face = Enum.NormalId.Top
    if size.Y > size.X then gui.Face = Enum.NormalId.Front end
    gui.Parent = part

    local label = Instance.new("TextLabel")
    label.Size = UDim2.new(1, 0, 1, 0)
    label.BackgroundTransparency = 1
    label.Text = text
    label.TextScaled = true
    label.TextColor3 = Color3.new(1, 1, 1)
    label.Parent = gui

    return part, label
end

-- Exchange
local exStation, _ = createStation("Exchange", Vector3.new(-15, 0.5, 0), Vector3.new(10, 1, 10), BrickColor.new("Bright green"), "TRADE "..EXCHANGE_CLICKS_COST.." CLICKS FOR COINS!")
local exDb = {}
exStation.Touched:Connect(function(hit)
    local player = Players:GetPlayerFromCharacter(hit.Parent)
    if player and not exDb[player.UserId] then
        exDb[player.UserId] = true
        local clicks = player.leaderstats.Clicks
        local coins = player.leaderstats.Coins
        if clicks.Value >= EXCHANGE_CLICKS_COST then
            clicks.Value = clicks.Value - EXCHANGE_CLICKS_COST
            local mult = calculateMultiplier(player)
            coins.Value = coins.Value + (EXCHANGE_COINS_REWARD * mult)
            exStation.BrickColor = BrickColor.new("Bright yellow")
            task.wait(0.5)
            exStation.BrickColor = BrickColor.new("Bright green")
        end
        exDb[player.UserId] = nil
    end
end)

-- Gacha
local gachaStation, _ = createStation("Gacha", Vector3.new(15, 4, 0), Vector3.new(8, 8, 8), BrickColor.new("Royal purple"), "BUY RANDOM PET\n500 COINS")
local gachaDb = {}
gachaStation.Touched:Connect(function(hit)
    local player = Players:GetPlayerFromCharacter(hit.Parent)
    if player and not gachaDb[player.UserId] then
        gachaDb[player.UserId] = true
        local coins = player.leaderstats.Coins
        if coins.Value >= 500 then
            coins.Value = coins.Value - 500
            local totalWeight = 0
            for _, p in ipairs(PET_LIST) do totalWeight = totalWeight + p.Chance end
            local rand = math.random(1, totalWeight)
            local current = 0
            local selectedPet = PET_LIST[1]
            for _, p in ipairs(PET_LIST) do
                current = current + p.Chance
                if rand <= current then selectedPet = p break end
            end
            local p = Instance.new("StringValue")
            p.Name = selectedPet.Name
            p:SetAttribute("Multiplier", selectedPet.Multiplier)
            p.Parent = player.Pets
            gachaStation.BrickColor = selectedPet.Color
            task.wait(1)
            gachaStation.BrickColor = BrickColor.new("Royal purple")
        end
        task.wait(0.5)
        gachaDb[player.UserId] = nil
    end
end)

-- Gate
local gateStation, gateLabel = createStation("Gate", Vector3.new(0, 7.5, -50), Vector3.new(20, 15, 2), BrickColor.new("Really black"), "UNLOCK WORLD 2\n" .. WORLD_UNLOCK_COST .. " COINS")
gateStation.Transparency = 0.5
gateStation.Touched:Connect(function(hit)
    local player = Players:GetPlayerFromCharacter(hit.Parent)
    if player then
        if player.UnlockedWorld2.Value then
            hit.Parent:PivotTo(CFrame.new(0, 5, -100))
        elseif player.leaderstats.Coins.Value >= WORLD_UNLOCK_COST then
            player.leaderstats.Coins.Value = player.leaderstats.Coins.Value - WORLD_UNLOCK_COST
            player.UnlockedWorld2.Value = true
            gateStation.Transparency = 0.8
            gateLabel.Text = "WORLD UNLOCKED!\nWALK THROUGH"
        end
    end
end)

-- Decor: World 2 Area
local w2Floor = Instance.new("Part")
w2Floor.Size = Vector3.new(100, 1, 100)
w2Floor.Position = Vector3.new(0, -0.5, -150)
w2Floor.Anchored = true
w2Floor.BrickColor = BrickColor.new("Sand blue")
w2Floor.Parent = game.Workspace

print("--- [SIMULATOR SCRIPT READY] ---")
