package com.epam.life.layout;

import java.awt.*;

/**
 * This class overrides default <code>GridLayout</code> layout manger
 * to reach a square cell always.
 */
public class CustomLayout extends GridLayout {

    /**
     * Creates a grid layout with the specified number of rows and
     * columns. All components in the layout are given equal size.
     * <p>
     * In addition, the horizontal and vertical gaps are set to the
     * specified values. Horizontal gaps are placed between each
     * of the columns. Vertical gaps are placed between each of
     * the rows.
     * <p>
     * One, but not both, of {@code rows} and {@code cols} can
     * be zero, which means that any number of objects can be placed in a
     * row or in a column.
     * <p>
     * All {@code GridLayout} constructors defer to this one.
     * @param     rows   the rows, with the value zero meaning
     *                   any number of rows
     * @param     cols   the columns, with the value zero meaning
     *                   any number of columns
     * @param     hgap   the horizontal gap
     * @param     vgap   the vertical gap
     * @exception   IllegalArgumentException  if the value of both
     *                  {@code rows} and {@code cols} is
     *                  set to zero
     */
    public CustomLayout(int rows, int cols, int hgap, int vgap) {
        super(rows, cols, hgap, vgap);
    }

    /**
     * Adds the specified component with the specified name to the layout.
     * @param name the name of the component
     * @param comp the component to be added
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        super.addLayoutComponent(name, comp);
    }

    /**
     * Removes the specified component from the layout.
     * @param comp the component to be removed
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        super.removeLayoutComponent(comp);
    }

    /**
     * Determines the preferred size of the container argument using
     * this grid layout.
     * <p>
     * The preferred width of a grid layout is the largest preferred
     * width of all of the components in the container times the number of
     * columns, plus the horizontal padding times the number of columns
     * minus one, plus the left and right insets of the target container.
     * <p>
     * The preferred height of a grid layout is the largest preferred
     * height of all of the components in the container times the number of
     * rows, plus the vertical padding times the number of rows minus one,
     * plus the top and bottom insets of the target container.
     * <p>
     *The most important thing in this method is that the cells of the grid
     * are always rectangle instead resizable.
     *
     * @param     parent   the container in which to do the layout
     * @return    the preferred dimensions to lay out the
     *                      subcomponents of the specified container
     * @see       java.awt.GridLayout#minimumLayoutSize
     * @see       java.awt.Container#getPreferredSize()
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int nComponents = parent.getComponentCount();
            int nRows = super.getRows();
            int nCols = super.getColumns();

            if (nRows > 0) {
                nCols = (nComponents + nRows - 1) / nRows;
            } else {
                nRows = (nComponents + nCols - 1) / nCols;
            }
            int w = 0;
            int h = 0;
            for (int i = 0 ; i < nComponents ; i++) {
                Component comp = parent.getComponent(i);
                Dimension d = comp.getPreferredSize();
                if (w < d.width) {
                    w = d.width;
                }
                if (h < d.height) {
                    h = d.height;
                }
            }
            int width = insets.left + insets.right + nCols * w + (nCols - 1) * super.getHgap();
            int height = insets.top + insets.bottom + nRows * h + (nRows - 1) * super.getVgap();
            if (width < height && nCols == nRows) {
                height = width;
            }
            if (height < width && nCols == nRows) {
                width = height;
            }
            return new Dimension(width, height);
        }
    }

    /**
     * Determines the minimum size of the container argument using this
     * grid layout.
     * <p>
     * The minimum width of a grid layout is the largest minimum width
     * of all of the components in the container times the number of columns,
     * plus the horizontal padding times the number of columns minus one,
     * plus the left and right insets of the target container.
     * <p>
     * The minimum height of a grid layout is the largest minimum height
     * of all of the components in the container times the number of rows,
     * plus the vertical padding times the number of rows minus one, plus
     * the top and bottom insets of the target container.
     * <p>
     * The most important thing in this method is that the cells of the grid
     * are always rectangle instead resizable and grid is always on center of component.
     *
     * @param       parent   the container in which to do the layout
     * @return      the minimum dimensions needed to lay out the
     *                      subcomponents of the specified container
     * @see         java.awt.GridLayout#preferredLayoutSize
     * @see         java.awt.Container#doLayout
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return super.minimumLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int nComponents = parent.getComponentCount();
            int nRows = super.getRows();
            int nCols = super.getColumns();
            boolean ltr = parent.getComponentOrientation().isLeftToRight();

            if (nComponents == 0) {
                return;
            }
            if (nRows > 0) {
                nCols = (nComponents + nRows - 1) / nRows;
            } else {
                nRows = (nComponents + nCols - 1) / nCols;
            }
            int totalGapsWidth = (nCols - 1) * super.getHgap();
            int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
            int widthOnComponent = (widthWOInsets - totalGapsWidth) / nCols;
            int extraWidthAvailable = (widthWOInsets - (widthOnComponent * nCols + totalGapsWidth)) / 2;

            int totalGapsHeight = (nRows - 1) * super.getVgap();
            int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
            int heightOnComponent = (heightWOInsets - totalGapsHeight) / nRows;
            int extraHeightAvailable = (heightWOInsets - (heightOnComponent * nRows + totalGapsHeight)) / 2;
            int deltaWidth = 0;
            if (widthOnComponent < heightOnComponent && nCols == nRows) {
                deltaWidth = heightOnComponent - widthOnComponent * nCols / 2;
                heightOnComponent = widthOnComponent;
            }
            if (heightOnComponent < widthOnComponent && nCols == nRows) {
                deltaWidth = (widthOnComponent - heightOnComponent) * nCols / 2;
                widthOnComponent = heightOnComponent;
            }
            if (ltr) {
                for (int c = 0, x = insets.left + extraWidthAvailable; c < nCols ; c++, x += widthOnComponent + super.getHgap()) {
                    for (int r = 0, y = insets.top + extraHeightAvailable; r < nRows ; r++, y += heightOnComponent + super.getVgap()) {
                        int i = r * nCols + c;
                        if (i < nComponents) {
                            parent.getComponent(i).setBounds(x + deltaWidth, y, widthOnComponent, heightOnComponent);
                        }
                    }
                }
            } else {
                for (int c = 0, x = (parent.getWidth() - insets.right - widthOnComponent) - extraWidthAvailable; c < nCols ; c++, x -= widthOnComponent + super.getHgap()) {
                    for (int r = 0, y = insets.top + extraHeightAvailable; r < nRows ; r++, y += heightOnComponent + super.getVgap()) {
                        int i = r * nCols + c;
                        if (i < nComponents) {
                            parent.getComponent(i).setBounds(x, y, widthOnComponent, heightOnComponent);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the string representation of this grid layout's values.
     * @return     a string representation of this grid layout
     */
    @Override
    public String toString() {
        return super.toString();
    }

}
