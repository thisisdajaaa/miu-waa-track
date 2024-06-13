import { memo, NamedExoticComponent } from "react";

import Comments from "./Comments";
import type { CommentsProps } from "./types";

const MemoizedComments: NamedExoticComponent<CommentsProps> = memo(Comments);

export default MemoizedComments;
