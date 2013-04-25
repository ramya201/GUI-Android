package homework2.android;

// based on code from java.awt.rectangle, copied from here:
// http://grepcode.com/file_/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/awt/Rectangle.java/?v=source
public class BoundaryRectangle {
	public int x,y,width,height;

	public BoundaryRectangle() {
        this(0, 0, 0, 0);
	}
	public BoundaryRectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
	}
    public BoundaryRectangle(BoundaryRectangle r) {
        this(r.x, r.y, r.width, r.height);
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public BoundaryRectangle getBounds() {
        return new BoundaryRectangle(x, y, width, height);
    }
    public void setBounds(BoundaryRectangle r) {
        setBounds(r.x, r.y, r.width, r.height);
    }
    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Translates this <code>BoundaryRectangle</code> the indicated distance,
     * to the right along the X coordinate axis, and
     * downward along the Y coordinate axis.
     * @param dx the distance to move this <code>Rectangle</code>
     *                 along the X axis
     * @param dy the distance to move this <code>Rectangle</code>
     *                 along the Y axis
     * @see       java.awt.Rectangle#setLocation(int, int)
     * @see       java.awt.Rectangle#setLocation(java.awt.Point)
     */
    public void translate(int dx, int dy) {
        int oldv = this.x;
        int newv = oldv + dx;
        if (dx < 0) {
            // moving leftward
            if (newv > oldv) {
                // negative overflow
                // Only adjust width if it was valid (>= 0).
                if (width >= 0) {
                    // The right edge is now conceptually at
                    // newv+width, but we may move newv to prevent
                    // overflow.  But we want the right edge to
                    // remain at its new location in spite of the
                    // clipping.  Think of the following adjustment
                    // conceptually the same as:
                    // width += newv; newv = MIN_VALUE; width -= newv;
                    width += newv - Integer.MIN_VALUE;
                    // width may go negative if the right edge went past
                    // MIN_VALUE, but it cannot overflow since it cannot
                    // have moved more than MIN_VALUE and any non-negative
                    // number + MIN_VALUE does not overflow.
                }
                newv = Integer.MIN_VALUE;
            }
        } else {
            // moving rightward (or staying still)
            if (newv < oldv) {
                // positive overflow
                if (width >= 0) {
                    // Conceptually the same as:
                    // width += newv; newv = MAX_VALUE; width -= newv;
                    width += newv - Integer.MAX_VALUE;
                    // With large widths and large displacements
                    // we may overflow so we need to check it.
                    if (width < 0) width = Integer.MAX_VALUE;
                }
                newv = Integer.MAX_VALUE;
            }
        }
        this.x = newv;

        oldv = this.y;
        newv = oldv + dy;
        if (dy < 0) {
            // moving upward
            if (newv > oldv) {
                // negative overflow
                if (height >= 0) {
                    height += newv - Integer.MIN_VALUE;
                    // See above comment about no overflow in this case
                }
                newv = Integer.MIN_VALUE;
            }
        } else {
            // moving downward (or staying still)
            if (newv < oldv) {
                // positive overflow
                if (height >= 0) {
                    height += newv - Integer.MAX_VALUE;
                    if (height < 0) height = Integer.MAX_VALUE;
                }
                newv = Integer.MAX_VALUE;
            }
        }
        this.y = newv;
    }
    /**
     * Checks whether or not this <code>Rectangle</code> contains the
     * point at the specified location {@code (x,y)}.
     *
     * @param  x the specified X coordinate
     * @param  y the specified Y coordinate
     * @return    <code>true</code> if the point
     *            {@code (x,y)} is inside this
     *            <code>Rectangle</code>;
     *            <code>false</code> otherwise.
     * @since     1.1
     */
    public boolean contains(int X, int Y) {
        int w = this.width;
        int h = this.height;
        if ((w | h) < 0) {
            // At least one of the dimensions is negative...
            return false;
        }
        // Note: if either dimension is zero, tests below must return false...
        int x = this.x;
        int y = this.y;
        if (X < x || Y < y) {
            return false;
        }
        w += x;
        h += y;
        //    overflow || intersect
        return ((w < x || w > X) &&
                (h < y || h > Y));
    }
    /**
     * Checks whether or not this <code>BoundaryRectangle</code> entirely contains
     * the specified <code>BoundaryRectangle</code>.
     *
     * @param     r   the specified <code>BoundaryRectangle</code>
     * @return    <code>true</code> if the <code>BoundaryRectangle</code>
     *            is contained entirely inside this <code>BoundaryRectangle</code>;
     *            <code>false</code> otherwise
     * @since     1.2
     */
    public boolean contains(BoundaryRectangle r) {
        return contains(r.x, r.y, r.width, r.height);
    }

    /**
     * Checks whether this <code>BoundaryRectangle</code> entirely contains
     * the <code>BoundaryRectangle</code>
     * at the specified location {@code (X,Y)} with the
     * specified dimensions {@code (W,H)}.
     * @param     X the specified X coordinate
     * @param     Y the specified Y coordinate
     * @param     W   the width of the <code>Rectangle</code>
     * @param     H   the height of the <code>Rectangle</code>
     * @return    <code>true</code> if the <code>Rectangle</code> specified by
     *            {@code (X, Y, W, H)}
     *            is entirely enclosed inside this <code>Rectangle</code>;
     *            <code>false</code> otherwise.
     * @since     1.1
     */
    public boolean contains(int X, int Y, int W, int H) {
        int w = this.width;
        int h = this.height;
        if ((w | h | W | H) < 0) {
            // At least one of the dimensions is negative...
            return false;
        }
        // Note: if any dimension is zero, tests below must return false...
        int x = this.x;
        int y = this.y;
        if (X < x || Y < y) {
            return false;
        }
        w += x;
        W += X;
        if (W <= X) {
            // X+W overflowed or W was zero, return false if...
            // either original w or W was zero or
            // x+w did not overflow or
            // the overflowed x+w is smaller than the overflowed X+W
            if (w >= x || W > w) return false;
        } else {
            // X+W did not overflow and W was not zero, return false if...
            // original w was zero or
            // x+w did not overflow and x+w is smaller than X+W
            if (w >= x && W > w) return false;
        }
        h += y;
        H += Y;
        if (H <= Y) {
            if (h >= y || H > h) return false;
        } else {
            if (h >= y && H > h) return false;
        }
        return true;
    }
    /**
     * Determines whether or not this <code>BoundaryRectangle</code> and the specified
     * <code>BoundaryRectangle</code> intersect. Two rectangles intersect if
     * their intersection is nonempty.
     *
     * @param r the specified <code>BoundaryRectangle</code>
     * @return    <code>true</code> if the specified <code>Rectangle</code>
     *            and this <code>Rectangle</code> intersect;
     *            <code>false</code> otherwise.
     */
    public boolean intersects(BoundaryRectangle r) {
        int tw = this.width;
        int th = this.height;
        int rw = r.width;
        int rh = r.height;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        int tx = this.x;
        int ty = this.y;
        int rx = r.x;
        int ry = r.y;
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }
    /**
     * Computes the intersection of this <code>Rectangle</code> with the
     * specified <code>Rectangle</code>. Returns a new <code>Rectangle</code>
     * that represents the intersection of the two rectangles.
     * If the two rectangles do not intersect, the result will be
     * an empty rectangle.
     *
     * @param     r   the specified <code>Rectangle</code>
     * @return    the largest <code>Rectangle</code> contained in both the
     *            specified <code>Rectangle</code> and in
     *            this <code>Rectangle</code>; or if the rectangles
     *            do not intersect, an empty rectangle.
     */
    public BoundaryRectangle intersection(BoundaryRectangle r) {
        int tx1 = this.x;
        int ty1 = this.y;
        int rx1 = r.x;
        int ry1 = r.y;
        long tx2 = tx1; tx2 += this.width;
        long ty2 = ty1; ty2 += this.height;
        long rx2 = rx1; rx2 += r.width;
        long ry2 = ry1; ry2 += r.height;
        if (tx1 < rx1) tx1 = rx1;
        if (ty1 < ry1) ty1 = ry1;
        if (tx2 > rx2) tx2 = rx2;
        if (ty2 > ry2) ty2 = ry2;
        tx2 -= tx1;
        ty2 -= ty1;
        // tx2,ty2 will never overflow (they will never be
        // larger than the smallest of the two source w,h)
        // they might underflow, though...
        if (tx2 < Integer.MIN_VALUE) tx2 = Integer.MIN_VALUE;
        if (ty2 < Integer.MIN_VALUE) ty2 = Integer.MIN_VALUE;
        return new BoundaryRectangle(tx1, ty1, (int) tx2, (int) ty2);
    }
    /**
     * Computes the union of this <code>BoundaryRectangle</code> with the
     * specified <code>BoundaryRectangle</code>. Returns a new
     * <code>Rectangle</code> that
     * represents the union of the two rectangles.
     * <p>
     * If either {@code BoundaryRectangle} has any dimension less than zero
     * the rules for <a href=#NonExistant>non-existant</a> rectangles
     * apply.
     * If only one has a dimension less than zero, then the result
     * will be a copy of the other {@code Rectangle}.
     * If both have dimension less than zero, then the result will
     * have at least one dimension less than zero.
     * <p>
     * If the resulting {@code Rectangle} would have a dimension
     * too large to be expressed as an {@code int}, the result
     * will have a dimension of {@code Integer.MAX_VALUE} along
     * that dimension.
     * @param r the specified <code>Rectangle</code>
     * @return    the smallest <code>Rectangle</code> containing both
     *            the specified <code>Rectangle</code> and this
     *            <code>Rectangle</code>.
     */
    public BoundaryRectangle union(BoundaryRectangle r) {
        long tx2 = this.width;
        long ty2 = this.height;
        if ((tx2 | ty2) < 0) {
            // This rectangle has negative dimensions...
            // If r has non-negative dimensions then it is the answer.
            // If r is non-existant (has a negative dimension), then both
            // are non-existant and we can return any non-existant rectangle
            // as an answer.  Thus, returning r meets that criterion.
            // Either way, r is our answer.
            return new BoundaryRectangle(r);
        }
        long rx2 = r.width;
        long ry2 = r.height;
        if ((rx2 | ry2) < 0) {
            return new BoundaryRectangle(this);
        }
        int tx1 = this.x;
        int ty1 = this.y;
        tx2 += tx1;
        ty2 += ty1;
        int rx1 = r.x;
        int ry1 = r.y;
        rx2 += rx1;
        ry2 += ry1;
        if (tx1 > rx1) tx1 = rx1;
        if (ty1 > ry1) ty1 = ry1;
        if (tx2 < rx2) tx2 = rx2;
        if (ty2 < ry2) ty2 = ry2;
        tx2 -= tx1;
        ty2 -= ty1;
        // tx2,ty2 will never underflow since both original rectangles
        // were already proven to be non-empty
        // they might overflow, though...
        if (tx2 > Integer.MAX_VALUE) tx2 = Integer.MAX_VALUE;
        if (ty2 > Integer.MAX_VALUE) ty2 = Integer.MAX_VALUE;
        return new BoundaryRectangle(tx1, ty1, (int) tx2, (int) ty2);
    }
    /**
     * Adds a point, specified by the integer arguments {@code newx,newy}
     * to the bounds of this {@code BoundaryRectangle}.
     * <p>
     * If this {@code BoundaryRectangle} has any dimension less than zero,
     * the rules for <a href=#NonExistant>non-existant</a>
     * rectangles apply.
     * In that case, the new bounds of this {@code BoundaryRectangle} will
     * have a location equal to the specified coordinates and
     * width and height equal to zero.
     * <p>
     * After adding a point, a call to <code>contains</code> with the
     * added point as an argument does not necessarily return
     * <code>true</code>. The <code>contains</code> method does not
     * return <code>true</code> for points on the right or bottom
     * edges of a <code>Rectangle</code>. Therefore, if the added point
     * falls on the right or bottom edge of the enlarged
     * <code>Rectangle</code>, <code>contains</code> returns
     * <code>false</code> for that point.
     * If the specified point must be contained within the new
     * {@code Rectangle}, a 1x1 rectangle should be added instead:
     * <pre>
     *     r.add(newx, newy, 1, 1);
     * </pre>
     * @param newx the X coordinate of the new point
     * @param newy the Y coordinate of the new point
     */
    public void add(int newx, int newy) {
        if ((width | height) < 0) {
            this.x = newx;
            this.y = newy;
            this.width = this.height = 0;
            return;
        }
        int x1 = this.x;
        int y1 = this.y;
        long x2 = this.width;
        long y2 = this.height;
        x2 += x1;
        y2 += y1;
        if (x1 > newx) x1 = newx;
        if (y1 > newy) y1 = newy;
        if (x2 < newx) x2 = newx;
        if (y2 < newy) y2 = newy;
        x2 -= x1;
        y2 -= y1;
        if (x2 > Integer.MAX_VALUE) x2 = Integer.MAX_VALUE;
        if (y2 > Integer.MAX_VALUE) y2 = Integer.MAX_VALUE;
        setBounds(x1, y1, (int) x2, (int) y2);
    }
    /**
     * Adds a <code>BoundaryRectangle</code> to this <code>Rectangle</code>.
     * The resulting <code>BoundaryRectangle</code> is the union of the two
     * rectangles.
     * <p>
     * If either {@code BoundaryRectangle} has any dimension less than 0, the
     * result will have the dimensions of the other {@code Rectangle}.
     * If both {@code BoundaryRectangle}s have at least one dimension less
     * than 0, the result will have at least one dimension less than 0.
     * <p>
     * If either {@code BoundaryRectangle} has one or both dimensions equal
     * to 0, the result along those axes with 0 dimensions will be
     * equivalent to the results obtained by adding the corresponding
     * origin coordinate to the result rectangle along that axis,
     * similar to the operation of the {@link #add(Point)} method,
     * but contribute no further dimension beyond that.
     * <p>
     * If the resulting {@code Rectangle} would have a dimension
     * too large to be expressed as an {@code int}, the result
     * will have a dimension of {@code Integer.MAX_VALUE} along
     * that dimension.
     * @param  r the specified <code>Rectangle</code>
     */
    public void add(BoundaryRectangle r) {
        long tx2 = this.width;
        long ty2 = this.height;
        if ((tx2 | ty2) < 0) {
            setBounds(r.x, r.y, r.width, r.height);
        }
        long rx2 = r.width;
        long ry2 = r.height;
        if ((rx2 | ry2) < 0) {
            return;
        }
        int tx1 = this.x;
        int ty1 = this.y;
        tx2 += tx1;
        ty2 += ty1;
        int rx1 = r.x;
        int ry1 = r.y;
        rx2 += rx1;
        ry2 += ry1;
        if (tx1 > rx1) tx1 = rx1;
        if (ty1 > ry1) ty1 = ry1;
        if (tx2 < rx2) tx2 = rx2;
        if (ty2 < ry2) ty2 = ry2;
        tx2 -= tx1;
        ty2 -= ty1;
        // tx2,ty2 will never underflow since both original
        // rectangles were non-empty
        // they might overflow, though...
        if (tx2 > Integer.MAX_VALUE) tx2 = Integer.MAX_VALUE;
        if (ty2 > Integer.MAX_VALUE) ty2 = Integer.MAX_VALUE;
        setBounds(tx1, ty1, (int) tx2, (int) ty2);
    }
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + ",width=" + width + ",height=" + height + "]";
    }
    /**
     * Checks whether two BoundaryRectangles are equal.
     * <p>
     * The result is <code>true</code> if and only if the argument is not
     * <code>null</code> and is a <code>BoundaryRectangle</code> object that has the
     * same upper-left corner, width, and height as
     * this <code>Rectangle</code>.
     * @param obj the <code>Object</code> to compare with
     *                this <code>Rectangle</code>
     * @return    <code>true</code> if the objects are equal;
     *            <code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof BoundaryRectangle) {
        	BoundaryRectangle r = (BoundaryRectangle)obj;
            return ((x == r.x) &&
                    (y == r.y) &&
                    (width == r.width) &&
                    (height == r.height));
        }
        return super.equals(obj);
    }
}
