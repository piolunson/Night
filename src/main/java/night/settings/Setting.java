/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.settings;


public class Setting {
    private final String name;
    private final String tag;
    private final String description;
    private final Visibility visibility;

    public String getName() {
        return this.name;
    }

    public String getTag() {
        return this.tag;
    }

    public String getDescription() {
        return this.description;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public Setting(String name, String tag, String description, Visibility visibility) {
        this.name = name;
        this.tag = tag;
        this.description = description;
        this.visibility = visibility;
    }

    public static class Visibility {
        private final Setting setting;
        private boolean visible = true;

        public Visibility() {
            this.setting = null;
        }

        public void update() {
        }

        public Setting getSetting() {
            return this.setting;
        }

        public boolean isVisible() {
            return this.visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public Visibility(Setting setting) {
            this.setting = setting;
        }
    }
}

