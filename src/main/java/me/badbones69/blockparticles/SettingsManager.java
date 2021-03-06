package me.badbones69.blockparticles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SettingsManager {

    private static SettingsManager instance = new SettingsManager();

    static SettingsManager getInstance() {
        return instance;
    }

    private BlockParticles p;

    private FileConfiguration config;
    private File cfile;

    private FileConfiguration data;
    private File dfile;

    void setup(BlockParticles p) {
        cfile = new File(p.getDataFolder(), "config.yml");
        config = p.getConfig();
        // config.options().copyDefaults(true);
        // saveConfig();

        if(!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }

        dfile = new File(p.getDataFolder(), "data.yml");
        if(!dfile.exists()) {
            try {
                File en = new File(p.getDataFolder(), "/data.yml");
                InputStream E = getClass().getResourceAsStream("/data.yml");
                copyFile(E, en);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        data = YamlConfiguration.loadConfiguration(dfile);
    }

    private static void copyFile(InputStream in, File out) throws Exception { // https://bukkit.org/threads/extracting-file-from-jar.16962/
        try (FileOutputStream fos = new FileOutputStream(out)) {
            byte[] buf = new byte[1024];
            int i;
            while ((i = in.read(buf)) != -1) fos.write(buf, 0, i);
        } finally {
            if (in != null) in.close();
        }
    }

    public FileConfiguration getData() {
        return data;
    }

    void saveData() {
        try {
            data.save(dfile);
        }catch(IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
        }
    }

    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dfile);
    }

    FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(cfile);
        }catch(IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
        }
    }

    void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public PluginDescriptionFile getDesc() {
        return p.getDescription();
    }
}