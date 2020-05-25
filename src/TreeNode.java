public class TreeNode {
    int label;
    int parentLabel;
    int bandwidth;
    int status; //unseen = 0, fringe = 1, inTree = 3
    TreeNode(int label){
        this.label = label;
        this.status = 0;
        this.parentLabel = -1;
    }
    TreeNode(int label, int bandwidth){
        this.label = label;
        this.bandwidth = bandwidth;
        this.status = 0;
        this.parentLabel = -1;
    }

}
