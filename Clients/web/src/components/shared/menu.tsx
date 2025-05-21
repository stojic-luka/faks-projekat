import { Children, useRef } from "react";
import { createPortal } from "react-dom";

interface TransparentPortalProps {
  children?: React.ReactNode;
  onClose: () => void;
  overlayClassName?: string;
  contentClassName?: string;
}
const Menu = ({ children, onClose, overlayClassName = "fixed inset-0 bg-transparent", contentClassName = "" }: TransparentPortalProps) => {
  const contentRef = useRef<HTMLDivElement>(null);

  const handleClickOutside = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if ((contentRef.current && !contentRef.current.contains(e.target as Node)) || Children.count(children) === 0) {
      onClose();
    }
  };

  if (Children.count(children) === 0) {
    return createPortal(<div className={overlayClassName} onMouseDown={onClose} />, document.body);
  }

  return createPortal(
    <div className={overlayClassName} onMouseDown={handleClickOutside}>
      <div ref={contentRef} className={contentClassName}>
        {children}
      </div>
    </div>,
    document.body
  );
};

export default Menu;
