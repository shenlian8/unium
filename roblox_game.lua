-- Roblox Advanced Clicker Game Script (Single Script Solution)
-- Place this in ServerScriptService

local DataStoreService = game:GetService("DataStoreService")
local playerDataStore = DataStoreService:GetDataStore("PlayerData_v1")
local RunService = game:GetService("RunService")

-- Configuration
local PET_LIST = {
    {Name = "Cat", Multiplier = 1.5, Chance = 50, Color = BrickColor.new("White")},
    {Name = "Dog", Multiplier = 2.0, Chance = 30, Color = BrickColor.new("Brown")},
    {Name = "Dragon", Multiplier = 5.0, Chance = 15, Color = BrickColor.new("Bright red")},
    {Name = "Godly", Multiplier = 20.0, Chance = 5, Color = BrickColor.new("Bright yellow")}
}

local WORLD_UNLOCK_COST = 5000

-- Helper: Calculate Multiplier
local function calculateMultiplier(player)
    local multiplier = 1
    local petsFolder = player:FindFirstChild("Pets")
    if petsFolder then
        for _, pet in ipairs(petsFolder:GetChildren()) do
            -- We assume all pets in the folder are "equipped" for simplicity
            multiplier = multiplier + (pet:GetAttribute("Multiplier") or 0)
        end
    end
    return multiplier
end

game.Players.PlayerAdded:Connect(function(player)
    -- 1. Leaderstats
    local leaderstats = Instance.new("Folder")
    leaderstats.Name = "leaderstats"
    leaderstats.Parent = player

    local clicks = Instance.new("IntValue")
    clicks.Name = "Clicks"
    clicks.Parent = leaderstats

    local coins = Instance.new("IntValue")
    coins.Name = "Coins"
    coins.Parent = leaderstats

    local unlockedWorld = Instance.new("BoolValue")
    unlockedWorld.Name = "UnlockedWorld2"
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
        clicks.Value = 0
        coins.Value = 0
        unlockedWorld.Value = false
    end

    -- 4. Clicker Tool Logic
    local function giveTool(targetBackpack)
        local tool = Instance.new("Tool")
        tool.Name = "Clicker"
        tool.RequiresHandle = false

        tool.Activated:Connect(function()
            local mult = calculateMultiplier(player)
            clicks.Value = clicks.Value + (1 * mult)
            coins.Value = coins.Value + (1 * mult)
        end)

        tool.Parent = targetBackpack
    end

    giveTool(player.Backpack)
    player.CharacterAdded:Connect(function() giveTool(player.Backpack) end)
end)

-- Save Data on Exit
game.Players.PlayerRemoving:Connect(function(player)
    local petNames = {}
    for _, pet in ipairs(player.Pets:GetChildren()) do
        table.insert(petNames, pet.Name)
    end

    local data = {
        Clicks = player.leaderstats.Clicks.Value,
        Coins = player.leaderstats.Coins.Value,
        UnlockedWorld2 = player.UnlockedWorld2.Value,
        Pets = petNames
    }

    pcall(function()
        playerDataStore:SetAsync(tostring(player.UserId), data)
    end)
end)

-- 5. PET GACHA STATION
local gachaPart = Instance.new("Part")
gachaPart.Name = "PetGacha"
gachaPart.Size = Vector3.new(8, 8, 8)
gachaPart.Position = Vector3.new(15, 4, 0)
gachaPart.Anchored = true
gachaPart.BrickColor = BrickColor.new("Royal purple")
gachaPart.Parent = game.Workspace

local gachaGui = Instance.new("SurfaceGui")
gachaGui.Face = Enum.NormalId.Front
gachaGui.Parent = gachaPart

local gachaLabel = Instance.new("TextLabel")
gachaLabel.Size = UDim2.new(1, 0, 1, 0)
gachaLabel.Text = "Buy Random Pet\nCost: 500 Coins"
gachaLabel.TextScaled = true
gachaLabel.Parent = gachaGui

local gachaDb = {}
gachaPart.Touched:Connect(function(hit)
    local player = game.Players:GetPlayerFromCharacter(hit.Parent)
    if player and not gachaDb[player.UserId] then
        gachaDb[player.UserId] = true
        local coins = player.leaderstats.Coins
        if coins.Value >= 500 then
            coins.Value = coins.Value - 500

            -- Weighted Random
            local totalWeight = 0
            for _, p in ipairs(PET_LIST) do totalWeight = totalWeight + p.Chance end
            local rand = math.random(1, totalWeight)
            local current = 0
            local selectedPet = PET_LIST[1]
            for _, p in ipairs(PET_LIST) do
                current = current + p.Chance
                if rand <= current then
                    selectedPet = p
                    break
                end
            end

            local p = Instance.new("StringValue")
            p.Name = selectedPet.Name
            p:SetAttribute("Multiplier", selectedPet.Multiplier)
            p.Parent = player.Pets

            print(player.Name .. " got a " .. selectedPet.Name .. "!")
            gachaPart.BrickColor = selectedPet.Color
            task.wait(1)
            gachaPart.BrickColor = BrickColor.new("Royal purple")
        end
        task.wait(0.5)
        gachaDb[player.UserId] = nil
    end
end)

-- 6. WORLD UNLOCK SYSTEM
local worldGate = Instance.new("Part")
worldGate.Name = "WorldGate"
worldGate.Size = Vector3.new(20, 15, 2)
worldGate.Position = Vector3.new(0, 7.5, -50)
worldGate.Anchored = true
worldGate.Transparency = 0.5
worldGate.CanCollide = true
worldGate.BrickColor = BrickColor.new("Really black")
worldGate.Parent = game.Workspace

local gateGui = Instance.new("SurfaceGui")
gateGui.Face = Enum.NormalId.Front
gateGui.Parent = worldGate

local gateLabel = Instance.new("TextLabel")
gateLabel.Size = UDim2.new(1, 0, 1, 0)
gateLabel.Text = "Unlock New World\nCost: " .. WORLD_UNLOCK_COST .. " Coins"
gateLabel.TextScaled = true
gateLabel.Parent = gateGui

worldGate.Touched:Connect(function(hit)
    local player = game.Players:GetPlayerFromCharacter(hit.Parent)
    if player then
        if player.UnlockedWorld2.Value then
            -- Teleport to world 2
            hit.Parent:SetPrimaryPartCFrame(CFrame.new(0, 5, -100))
        elseif player.leaderstats.Coins.Value >= WORLD_UNLOCK_COST then
            player.leaderstats.Coins.Value = player.leaderstats.Coins.Value - WORLD_UNLOCK_COST
            player.UnlockedWorld2.Value = true
            worldGate.CanCollide = false
            worldGate.Transparency = 0.8
            gateLabel.Text = "WORLD UNLOCKED!\nWalk through to enter"
            print(player.Name .. " unlocked World 2!")
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

print("Advanced Clicker Game Loaded!")
