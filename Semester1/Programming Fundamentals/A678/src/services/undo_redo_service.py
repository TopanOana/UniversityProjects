class UndoRedoService:
    def __init__(self, undo_redo_repo):
        """the undo redo repo on which to call specific undo or redo functions"""
        self.__repo = undo_redo_repo

    def undo(self):
        """calls the undo function on the undo redo repo"""
        self.__repo.undo()

    def redo(self):
        """calls the redo function on the undo redo repo"""
        self.__repo.redo()
