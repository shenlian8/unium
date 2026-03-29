-- ROBLOX SIMULATOR SCRIPT (DEBUGGED & OPTIMIERT)
-- Platziere dieses Skript in: ServerScriptService

local DataStoreService = game:GetService("DataStoreService")
local playerDataStore = DataStoreService:GetDataStore("SimulatorData_Final_v2") -- Version v2
local Players = game:GetService("Players")

-- EINSTELLUNGEN
local PET_LIST = {
	{Name = "Katze", Multiplier = 1.5, Chance = 50, Color = BrickColor.new("White")},
	{Name = "Hund", Multiplier = 2.0, Chance = 30, Color = BrickColor.new("Brown")},
	{Name = "Drache", Multiplier = 5.0, Chance = 15, Color = BrickColor.new("Bright red")},
	{Name = "Gottlich", Multiplier = 20.0, Chance = 5, Color = BrickColor.new("Bright yellow")}
}

local WORLD_UNLOCK_COST = 5000
local EXCHANGE_CLICKS_COST = 100
local EXCHANGE_COINS_REWARD = 50

print("--- [SIMULATOR DEBUG MODE LÄDT...] ---")

-- HILFSFUNKTION: Multiplikator berechnen
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

-- DATEN SPEICHERN LOGIK
local function savePlayerData(player)
	local success, err = pcall(function()
		local petNames = {}
		if player:FindFirstChild("Pets") then
			for _, pet in ipairs(player.Pets:GetChildren()) do
				table.insert(petNames, pet.Name)
			end
		end

		local data = {
			Klicks = player.leaderstats.Klicks.Value,
			Munzen = player.leaderstats.Munzen.Value,
			UnlockedWorld2 = player.UnlockedWorld2.Value,
			Pets = petNames
		}
		playerDataStore:SetAsync(tostring(player.UserId), data)
	end)
	if not success then warn("Fehler beim Speichern von " .. player.Name .. ": " .. err) end
end

-- SPIELER-SETUP
local function onPlayerAdded(player)
	-- 1. Leaderstats SOFORT erstellen (wichtig für Sichtbarkeit)
	local leaderstats = Instance.new("Folder")
	leaderstats.Name = "leaderstats"
	leaderstats.Parent = player

	-- NumberValue statt IntValue für korrekte Multiplikation
	local clicks = Instance.new("NumberValue")
	clicks.Name = "Klicks"
	clicks.Value = 0
	clicks.Parent = leaderstats

	local coins = Instance.new("NumberValue")
	coins.Name = "Munzen"
	coins.Value = 0
	coins.Parent = leaderstats

	local unlockedWorld = Instance.new("BoolValue")
	unlockedWorld.Name = "UnlockedWorld2"
	unlockedWorld.Value = false
	unlockedWorld.Parent = player

	local petsFolder = Instance.new("Folder")
	petsFolder.Name = "Pets"
	petsFolder.Parent = player

	-- 2. Daten laden (Asynchron)
	task.spawn(function()
		local success, data = pcall(function()
			return playerDataStore:GetAsync(tostring(player.UserId))
		end)

		if success and data then
			clicks.Value = data.Klicks or 0
			coins.Value = data.Munzen or 0
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
		end
	end)

	-- 3. Klicker-Tool
	local function giveTool()
		local backpack = player:WaitForChild("Backpack", 5)
		if not backpack then return end

		local old = backpack:FindFirstChild("Klicker") or (player.Character and player.Character:FindFirstChild("Klicker"))
		if old then old:Destroy() end

		local tool = Instance.new("Tool")
		tool.Name = "Klicker"
		tool.RequiresHandle = false

		tool.Activated:Connect(function()
			local mult = calculateMultiplier(player)
			clicks.Value = clicks.Value + (1 * mult)
			coins.Value = coins.Value + (0.5 * mult) -- Jetzt korrekt dank NumberValue
		end)

		tool.Parent = backpack
	end

	giveTool()
	player.CharacterAdded:Connect(giveTool)
end

Players.PlayerAdded:Connect(onPlayerAdded)
for _, player in ipairs(Players:GetPlayers()) do onPlayerAdded(player) end

Players.PlayerRemoving:Connect(savePlayerData)
game:BindToClose(function()
	for _, player in ipairs(Players:GetPlayers()) do
		savePlayerData(player)
	end
	task.wait(2) -- Zeit zum Speichern geben
end)

-- 4. WELT-OBJEKTE (Verbesserte GUIs)
local function createPart(name, pos, size, color, text, guiFace)
	local part = Instance.new("Part")
	part.Name = name
	part.Position = pos
	part.Size = size
	part.Anchored = true
	part.BrickColor = color
	part.Parent = game.Workspace

	local gui = Instance.new("SurfaceGui")
	gui.Face = guiFace or Enum.NormalId.Top
	gui.Parent = part

	local label = Instance.new("TextLabel")
	label.Size = UDim2.new(1, 0, 1, 0)
	label.BackgroundTransparency = 1
	label.Text = text or ""
	label.TextScaled = true
	label.TextColor3 = Color3.new(1, 1, 1)
	label.Parent = gui

	return part, label
end

-- Startbereich
createPart("Floor1", Vector3.new(0, -0.5, 0), Vector3.new(100, 1, 100), BrickColor.new("Grime"), "START")

-- Tausch (Top)
local exStation, _ = createPart("Exchange", Vector3.new(-20, 0.5, 0), Vector3.new(10, 1, 10), BrickColor.new("Bright green"), "TRADE: "..EXCHANGE_CLICKS_COST.." KLICKS -> MUNZEN", Enum.NormalId.Top)
local exDb = {}
exStation.Touched:Connect(function(hit)
	local player = Players:GetPlayerFromCharacter(hit.Parent)
	if player and not exDb[player.UserId] then
		exDb[player.UserId] = true
		if player.leaderstats.Klicks.Value >= EXCHANGE_CLICKS_COST then
			player.leaderstats.Klicks.Value -= EXCHANGE_CLICKS_COST
			local mult = calculateMultiplier(player)
			player.leaderstats.Munzen.Value += (EXCHANGE_COINS_REWARD * mult)
			exStation.BrickColor = BrickColor.new("Bright yellow")
			task.wait(0.5)
			exStation.BrickColor = BrickColor.new("Bright green")
		end
		exDb[player.UserId] = nil
	end
end)

-- Gacha (Vorne)
local gachaStation, _ = createPart("Gacha", Vector3.new(20, 4, 0), Vector3.new(8, 8, 8), BrickColor.new("Royal purple"), "PET SHOP\n500 MUNZEN", Enum.NormalId.Front)
local gachaDb = {}
gachaStation.Touched:Connect(function(hit)
	local player = Players:GetPlayerFromCharacter(hit.Parent)
	if player and not gachaDb[player.UserId] then
		gachaDb[player.UserId] = true
		if player.leaderstats.Munzen.Value >= 500 then
			player.leaderstats.Munzen.Value -= 500
			local totalWeight = 0
			for _, p in ipairs(PET_LIST) do totalWeight += p.Chance end
			local rand = math.random(1, totalWeight)
			local current = 0
			local selectedPet = PET_LIST[1]
			for _, p in ipairs(PET_LIST) do
				current += p.Chance
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

-- Tor (Vorne)
local gateStation, gateLabel = createPart("Gate", Vector3.new(0, 7.5, -50), Vector3.new(20, 15, 2), BrickColor.new("Really black"), "WELT 2\n" .. WORLD_UNLOCK_COST .. " MUNZEN", Enum.NormalId.Front)
gateStation.Transparency = 0.5
gateStation.Touched:Connect(function(hit)
	local player = Players:GetPlayerFromCharacter(hit.Parent)
	if player then
		if player.UnlockedWorld2.Value then
			hit.Parent:PivotTo(CFrame.new(0, 5, -120))
		elseif player.leaderstats.Munzen.Value >= WORLD_UNLOCK_COST then
			player.leaderstats.Munzen.Value -= WORLD_UNLOCK_COST
			player.UnlockedWorld2.Value = true
			gateStation.Transparency = 0.8
			gateLabel.Text = "FREIGESCHALTET!"
		end
	end
end)

createPart("Floor2", Vector3.new(0, -0.5, -150), Vector3.new(100, 1, 100), BrickColor.new("Sand blue"), "WELT 2")

print("--- [SIMULATOR DEBUGGED!] ---")
