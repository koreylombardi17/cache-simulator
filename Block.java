public class Block {

	// Each cache block has a tag and a dirty bit
    private long tag;
    private boolean dirtyBit;

    public Block(long tag) {
        this.tag = tag;
        dirtyBit = false;
    }

    public long getTag() {
        return tag;
    }

    public boolean isDirtyBit() {
        return dirtyBit;
    }

    public void setDirtyBit(boolean dirtyBit) {
        this.dirtyBit = dirtyBit;
    }
}
