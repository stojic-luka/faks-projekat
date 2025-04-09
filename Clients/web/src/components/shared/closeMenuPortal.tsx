import { createPortal } from "react-dom";

interface TransparentPortalProps {
  onClose: () => void;
  overlayClassName?: string;
}
const CloseMenuPortal = ({ onClose, overlayClassName = "fixed inset-0 bg-transparent" }: TransparentPortalProps) => {
  return createPortal(<div className={overlayClassName} onMouseDown={onClose} />, document.body);
};

export default CloseMenuPortal;
