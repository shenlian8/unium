-- ROBLOX PET MULTIPLIER SYSTEM (SPEZIAL-CODE)
-- Dieser Code zeigt genau, wie Haustiere (Pets) deine Klicks und Münzen vervielfachen!

local Players = game:GetService("Players")

-- PET LISTE: Name und der Multiplikator (z.B. 2.0 = Doppelte Klicks)
local PETS_DATABASE = {
	{Name = "Kleiner Hund", Multiplier = 1.5, Price = 250, Color = BrickColor.new("Brown")},
	{Name = "Schildkrote", Multiplier = 2.0, Price = 500, Color = BrickColor.new("Bright green")},
	{Name = "Phonix", Multiplier = 5.0, Price = 2500, Color = BrickColor.new("Bright orange")},
	{Name = "Gott", Multiplier = 10.0, Price = 10000, Color = BrickColor.new("New Yeller")}
}

-- HILFSFUNKTION: Multiplikator berechnen
-- Diese Funktion zählt alle Multiplikatoren deiner Pets zusammen.
local function getPlayerMultiplier(player)
	local totalMult = 1 -- Startwert (1x)
	local petsFolder = player:FindFirstChild("Pets")
	if petsFolder then
		for _, pet in ipairs(petsFolder:GetChildren()) do
			totalMult = totalMult + (pet:GetAttribute("Multiplier") or 0)
		end
	end
	return totalMult
end

Players.PlayerAdded:Connect(function(player)
	-- Leaderstats
	local leaderstats = Instance.new("Folder")
	leaderstats.Name = "leaderstats"
	leaderstats.Parent = player

	local klicks = Instance.new("IntValue")
	klicks.Name = "Klicks"
	klicks.Value = 0
	klicks.Parent = leaderstats

	local munzen = Instance.new("IntValue")
	munzen.Name = "Munzen"
	munzen.Value = 100 -- Startkapital
	munzen.Parent = leaderstats

	-- Pets Ordner
	local petsFolder = Instance.new("Folder")
	petsFolder.Name = "Pets"
	petsFolder.Parent = player

	-- Klicker Werkzeug mit Multiplikator
	local function giveClicker()
		local backpack = player:WaitForChild("Backpack", 5)
		if not backpack then return end

		local tool = Instance.new("Tool")
		tool.Name = "Pet-Klicker"
		tool.RequiresHandle = false

		tool.Activated:Connect(function()
			local mult = getPlayerMultiplier(player)
			klicks.Value = klicks.Value + (1 * mult)
			munzen.Value = munzen.Value + (1 * mult)
			print(player.Name .. " klickt mit Multiplikator: " .. mult)
		end)

		tool.Parent = backpack
	end

	giveClicker()
	player.CharacterAdded:Connect(giveClicker)
end)

-- PET SHOP STATION
-- Eine Station, an der man Pets kaufen kann.
local shop = Instance.new("Part")
shop.Name = "PetShop"
shop.Size = Vector3.new(10, 10, 10)
shop.Position = Vector3.new(0, 5, 20)
shop.Anchored = true
shop.BrickColor = BrickColor.new("Electric blue")
shop.Parent = game.Workspace

local gui = Instance.new("SurfaceGui")
gui.Face = Enum.NormalId.Front
gui.Parent = shop

local label = Instance.new("TextLabel")
label.Size = UDim2.new(1, 0, 1, 0)
label.Text = "PET SHOP\nTritt hier zum Kaufen!\nZufall: 250 Munzen"
label.TextScaled = true
label.TextColor3 = Color3.new(1, 1, 1)
label.BackgroundTransparency = 1
label.Parent = gui

-- Kauf-Logik
local db = false
shop.Touched:Connect(function(hit)
	local player = Players:GetPlayerFromCharacter(hit.Parent)
	if player and not db then
		db = true
		local coins = player.leaderstats.Munzen
		if coins.Value >= 250 then
			coins.Value = coins.Value - 250

			-- Zufälliges Pet auswählen
			local petInfo = PETS_DATABASE[math.random(1, #PETS_DATABASE)]

			local newPet = Instance.new("StringValue")
			newPet.Name = petInfo.Name
			newPet:SetAttribute("Multiplier", petInfo.Multiplier)
			newPet.Parent = player.Pets

			warn(player.Name .. " hat ein Pet bekommen: " .. petInfo.Name)
			shop.BrickColor = petInfo.Color
			task.wait(1)
			shop.BrickColor = BrickColor.new("Electric blue")
		end
		task.wait(0.5)
		db = false
	end
end)

print("Pet-Multiplikator-System geladen!")
